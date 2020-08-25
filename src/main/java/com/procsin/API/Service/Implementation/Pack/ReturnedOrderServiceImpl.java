package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.Return.PRSOrderDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedOrderDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedOrderLogDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedProductDAO;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.ReturnOrderRequestModel;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.DB.Entity.Pack.Return.*;
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
    PRSOrderDAO PRSOrderDAO;
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
        PRSOrder order = PRSOrderDAO.findByOrderCode(requestModel.orderModel.OrderCode);
        if (order == null) {
            order = createOrder(requestModel.orderModel, getActiveUser());
        }
        ReturnedOrder returnedOrder = returnedOrderDAO.findByOrderAndIsCompleted(order,false);
        if (returnedOrder != null) {
            returnedOrder.productsDidReturn = true;
            returnedOrderDAO.save(returnedOrder);
            createReturnedProducts(requestModel.products, returnedOrder, getActiveUser());
            return updateReturnedOrder(returnedOrder.id, ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR,
                    returnedOrder.description, returnedOrder.trackingCode);
        }
        else {
            returnedOrder  = new ReturnedOrder();
            returnedOrder.order = order;
            returnedOrder.status = ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR;
            returnedOrder.description = "";
            returnedOrder.trackingCode = "";
            returnedOrder.createdAt = new Date();
            returnedOrder.createdBy = getActiveUser();
            returnedOrder.lastUpdatedAt = new Date();
            returnedOrder.lastUpdatedBy = getActiveUser();
            returnedOrder.productsDidReturn = true;
            returnedOrder.manuallyCreated = false;
            returnedOrder.newOrderCreated = false;
            returnedOrder.isCompleted = false;

            returnedOrder = returnedOrderDAO.save(returnedOrder);
        }
        updateReturnedOrder(returnedOrder.id, ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR, null, null);
        createReturnedProducts(requestModel.products, returnedOrder, getActiveUser());
        return returnedOrder;
    }

    private PRSOrder createOrder(OrderModel orderModel, User user) {
        PRSOrder order = new PRSOrder(orderModel, user);
        return PRSOrderDAO.save(order);
    }

    private void createReturnedProducts(List<ReturnedProduct> returnedProducts, ReturnedOrder returnedOrder, User user) {
        for (ReturnedProduct returnedProduct : returnedProducts) {
            returnedProduct.returnedOrder = returnedOrder;
            returnedProduct.createdAt = new Date();
            returnedProduct.createdBy = user;
            returnedProductDAO.save(returnedProduct);
        }
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
                createReturnedOrderLog(id, returnedOrder.status, returnedOrder.description);
            }

            return returnedOrderDAO.save(returnedOrder);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public List<ReturnedOrder> allReturnedOrders() {
        List<ReturnedOrder> allOrders = new ArrayList<>();
        returnedOrderDAO.findAll().iterator().forEachRemaining(allOrders::add);
        return allOrders;
    }

    @Override
    public List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrderStatus status) {
        return returnedOrderDAO.findAllByStatus(status);
    }

    @Override
    public List<ReturnedOrder> waitingReturnedOrders() {
        return returnedOrdersByStatus(ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR);
    }

    @Override
    public void updateRequiredFields(String token) {
//        List<ReturnedOrder> orders = returnedOrderDAO.findAllByOrderTypeIsNull();
//        for (ReturnedOrder returnedOrder : orders) {
//                OrderModel temp = orderService.getSpecificOrder(token, returnedOrder.orderCode);
//                if (temp != null) {
//                    returnedOrder.orderType = temp.PaymentType;
//                    returnedOrder.orderAmount = temp.OrderTotalPrice;
//                    returnedOrderDAO.save(returnedOrder);
//                }
//        }
    }

    @Override
    public ReturnedOrder findReturnedOrder(String orderCode) {
        PRSOrder prsOrder = PRSOrderDAO.findByOrderCode(orderCode);
        return returnedOrderDAO.findByOrderAndIsCompleted(prsOrder, false);
    }

    private ReturnedOrderLog createReturnedOrderLog(Long id, ReturnedOrderStatus status, String description) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findById(id).isPresent() ? returnedOrderDAO.findById(id).get() : null;
        if (returnedOrder == null) {
            throw new IllegalArgumentException();
        }
        ReturnedOrderLog log = new ReturnedOrderLog(returnedOrder,getActiveUser(),status,description);
        return returnedOrderLogDAO.save(log);
    }


}
