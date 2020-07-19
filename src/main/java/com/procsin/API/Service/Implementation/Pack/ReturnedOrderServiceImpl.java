package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.Return.ReturnedOrderDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedOrderLogDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedProductDAO;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.ReturnOrderRequestModel;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderStatus;
import com.procsin.DB.Entity.Pack.Return.ReturnedProduct;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReturnedOrderServiceImpl implements ReturnedOrderService {

    @Autowired
    UserDao userRepository;

    @Autowired
    ReturnedOrderDAO returnedOrderDAO;

    @Autowired
    ReturnedOrderLogDAO returnedOrderLogDAO;

    @Autowired
    ReturnedProductDAO returnedProductDAO;

    @Autowired
    OrderService orderService;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public ReturnedOrder createReturnedOrder(ReturnOrderRequestModel requestModel) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findByOrderCode(requestModel.orderModel.OrderCode);
        if (returnedOrder != null) {
            returnedOrder.productsDidReturn = true;
            if (shouldChangeStatus(returnedOrder,ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR)) {
                return updateReturnedOrder(returnedOrder.id, ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR,
                        returnedOrder.description, returnedOrder.trackingCode);
            }
        }
        else {
            returnedOrder  = new ReturnedOrder(requestModel.orderModel,getActiveUser());
            returnedOrder = returnedOrderDAO.save(returnedOrder);
        }
        for (ReturnedProduct returnedProduct : requestModel.products) {
            returnedProduct.returnedOrder = returnedOrder;
            returnedProductDAO.save(returnedProduct);
        }

        return returnedOrder;
    }

    @Override
    public ReturnedOrder updateReturnedOrder(Long id, ReturnedOrderStatus status, String description, String trackingCode) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findById(id).isPresent() ? returnedOrderDAO.findById(id).get() : null;
        if (returnedOrder != null) {
            boolean didUpdate = false;
            if (status != null) {
                returnedOrder.status = status;
                didUpdate = true;
            }
            if (description != null) {
                returnedOrder.description = description;
                didUpdate = true;
            }

            if (trackingCode != null) {
                returnedOrder.trackingCode = trackingCode;
                didUpdate = true;
            }

            if (didUpdate) {
                returnedOrder.lastUpdatedAt = new Date();
                returnedOrder.lastUpdatedBy = getActiveUser();
            }

            return returnedOrderDAO.save(returnedOrder);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public ReturnedOrderLog changeReturnedOrderStatus(Long id, ReturnedOrderStatus status) {
        updateReturnedOrder(id,status,null,null);
        return createReturnedOrderLog(id,status);
    }

    @Override
    public List<ReturnedOrder> allReturnedOrders() {
        List<ReturnedOrder> allOrders = new ArrayList<>();
        returnedOrderDAO.findAll().iterator().forEachRemaining(allOrders::add);
        return allOrders;
    }

    @Override
    public List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrderStatus status) {
        List<ReturnedOrder> returnedOrders = returnedOrderDAO.findAllByStatus(status);
        return returnedOrders;
    }

    @Override
    public List<ReturnedOrder> waitingReturnedOrders() {
        return returnedOrdersByStatus(ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR);
    }

    @Override
    public void updateRequiredFields(String token) {
        List<ReturnedOrder> orders = returnedOrderDAO.findAllByOrderTypeIsNull();
        for (ReturnedOrder returnedOrder : orders) {
                OrderModel temp = orderService.getSpecificOrder(token, returnedOrder.orderCode);
                if (temp != null) {
                    returnedOrder.orderType = temp.PaymentType;
                    returnedOrder.orderAmount = temp.OrderTotalPrice;
                    returnedOrderDAO.save(returnedOrder);
                }
        }
    }

    private boolean shouldChangeStatus(ReturnedOrder returnedOrder, ReturnedOrderStatus newStatus) {
        boolean shouldChange = false;

        if (returnedOrder.isCompleted || newStatus.equals(ReturnedOrderStatus.MANUEL_OLUSTURULDU)) {
            return false;
        }

        switch (returnedOrder.status) {
            case YENIDEN_CIKIS_BEKLENIYOR:
                if (newStatus.equals(ReturnedOrderStatus.YENIDEN_CIKIS_SAGLANDI))
                    shouldChange = true;
                break;
            case KISMI_GONDERIM_BEKLENIYOR:
                if (newStatus.equals(ReturnedOrderStatus.KISMI_GONDERIM_SAGLANDI))
                    shouldChange = true;
                break;
        }
        return shouldChange;
    }

    private ReturnedOrderLog createReturnedOrderLog(Long id, ReturnedOrderStatus status) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findById(id).isPresent() ? returnedOrderDAO.findById(id).get() : null;
        ReturnedOrderLog log = new ReturnedOrderLog(returnedOrder,status,getActiveUser());
        return returnedOrderLogDAO.save(log);
    }


}
