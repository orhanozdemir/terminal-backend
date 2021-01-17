package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.ErrorLogDao;
import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.API.Service.Interface.Pack.*;
import com.procsin.DB.Entity.ErrorLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderStatus;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.ProductModel;
import com.procsin.Retrofit.Models.TSoft.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ErrorLogDao errorLogDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderLogDao orderLogDao;

    @Autowired
    TsoftService tsoftService;
    @Autowired
    OrderLogService orderLogService;
    @Autowired
    IISService iisService;
    @Autowired
    ReturnedOrderService returnedOrderService;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDao.findByUsername(userDetails.getUsername());
    }

    private void createErrorLog(String errorCode, String errorMessage) {
        ErrorLog errorLog = new ErrorLog(errorCode,errorMessage,getActiveUser().getId());
        errorLogDao.save(errorLog);
    }

    @Override
    public OrderLogSuccessModel getSingleOrder(String token, boolean isTrendyol) {
        try {
            if (token == null) {
                token = tsoftService.getTsoftToken();
            }
            return tsoftService.getTSoftOrder(token,isTrendyol);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public OrderModel getSpecificOrder(String token, String orderCode) {
        try {
            if (token == null) {
                token = tsoftService.getTsoftToken();
            }
            OrderModel order = tsoftService.getSingleOrder(token,orderCode);
            for (ProductModel model : order.OrderDetails) {
                order.totalProductCount += model.Quantity;
            }

            return order;
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public GenericResponse finishOrder(String token, boolean isReturn, String orderCode) {
        if (isPackedBefore(orderCode)) {
            String errorMessage = "Bu sipariş daha önce tamamlanmış";
            createErrorLog("Order-1007",errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        else {
            Orders order = orderDao.findByOrderCode(orderCode);
            if (order == null) {
                String errorMessage = "Bu sipariş hiç oluşturulmamış";
                createErrorLog("Order-1008",errorMessage + " / " + orderCode);
                throw new IllegalStateException(errorMessage);
            }
            try {
                if (token == null) {
                    token = tsoftService.getTsoftToken();
                }
                GenericTsoftResponseModel response = tsoftService.updateOrderStatus(token,false,orderCode,Orders.OrderStatusEnum.PACKED);
                if (response == null || !response.success) {
                    String errorMessage = "Sipariş tamamlanırken bir sorun oluştu.";
                    createErrorLog("Order-1009",errorMessage + " / " + orderCode);
                    throw new IllegalStateException(errorMessage);
                }
                orderLogService.createOrderLog(order,OrderLog.OrderStatus.KARGO_HAZIR);
                if (!isReturn) {
                    iisService.createInvoice(orderCode);
                }
                else {
                    ReturnedOrder returnedOrder = returnedOrderService.findReturnedOrder(orderCode);
                    returnedOrderService.updateReturnedOrder(returnedOrder.id, ReturnedOrderStatus.YENIDEN_CIKIS_SAGLANDI,null,null);
                }
                return new GenericResponse(true,"Başarılı");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String errorMessage = "Sipariş tamamlanırken bir sorun oluştu.";
        createErrorLog("Order-1009",errorMessage + " / " + orderCode);
        throw new IllegalStateException(errorMessage);
    }

    @Override
    public GenericResponse updateToSupplement(String token, boolean isReturn, String orderCode) {
        Orders order = orderDao.findByOrderCode(orderCode);
        if (order == null) {
            String errorCode = "Order-1003";
            String errorMessage = errorCode + "/" + "Bu sipariş hiç oluşturulmamış";
            createErrorLog(errorCode,errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        if (isPackedBefore(orderCode)) {
            String errorCode = "Order-1004";
            String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
            createErrorLog(errorCode,errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        try {
            if (token == null) {
                token = tsoftService.getTsoftToken();
            }
            GenericTsoftResponseModel response = tsoftService.updateOrderStatus(token,isReturn, orderCode, Orders.OrderStatusEnum.NEED_SUPPLY);
            if (response == null || !response.success) {
                throw new IllegalStateException("-");
            }
            orderLogService.createOrderLog(order,OrderLog.OrderStatus.TEDARIK_SURECINDE);
            return new GenericResponse(true,"Başarılı");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GenericResponse(false,"Hata");
    }

    @Override
    public GenericResponse cancelOrder(String token, boolean isReturn, String orderCode) {
        Orders order = orderDao.findByOrderCode(orderCode);
        if (order == null) {
            String errorCode = "Order-1005";
            String errorMessage = errorCode + "/" + "Bu sipariş hiç oluşturulmamış";
            createErrorLog(errorCode,errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        if (isPackedBefore(orderCode)) {
            String errorCode = "Order-1006";
            String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
            createErrorLog(errorCode,errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        try {
            if (token == null) {
                token = tsoftService.getTsoftToken();
            }
            OrderModel temp = getSpecificOrder(token, orderCode);
            if (temp.OrderStatusId.equals(StatusEnum.URUN_PAKETLENIYOR.statusId)) {
                GenericTsoftResponseModel response = tsoftService.updateOrderStatus(token,isReturn,orderCode, Orders.OrderStatusEnum.CANCELED);
                if (response == null || !response.success) {
                    String errorCode = "Order-1010";
                    String errorMessage = errorCode + "/" + "sipariş iptal edilirken sorun oluştu.";
                    createErrorLog(errorCode,errorMessage + " / " + orderCode);
                    throw new IllegalStateException(errorMessage);
                }
            }
            orderLogService.createOrderLog(order,OrderLog.OrderStatus.URUN_PAKETLENIYOR_IPTAL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String errorCode = "Order-1010";
        String errorMessage = errorCode + "/" + "sipariş iptal edilirken sorun oluştu.";
        createErrorLog(errorCode,errorMessage + " / " + orderCode);
        throw new IllegalStateException(errorMessage);
    }

    @Override
    public OrderLogSuccessModel getReturnedOrder(String token) {
        try {
            if (token == null) {
                token = tsoftService.getTsoftToken();
            }
            return tsoftService.getReturnedOrder(token);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isOrderAvailable(String orderCode) {
        List<OrderLog> logs = orderLogDao.findAllByOrderCode(orderCode);
        if (logs != null && logs.size() > 0) {
            OrderLog lastLog = logs.get(logs.size() - 1);
            if (lastLog != null) {
                if (lastLog.getStatus() == OrderLog.OrderStatus.KARGO_HAZIR) {
                    String errorCode = "Order-1001";
                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
                    createErrorLog(errorCode,errorMessage + " / " + orderCode);
                    return false;
                }
                if (lastLog.getStatus() == OrderLog.OrderStatus.URUN_PAKETLENIYOR) {
                    String errorCode = "Order-1002";
                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce işleme alınmış";
                    createErrorLog(errorCode,errorMessage + " / " + orderCode);
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPackedBefore(String orderCode) {
        return orderLogDao.findReadyByOrderCode(orderCode) != null;
    }

}
