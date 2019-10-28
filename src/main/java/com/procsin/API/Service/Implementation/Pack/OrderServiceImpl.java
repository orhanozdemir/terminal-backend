package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.ErrorLogDao;
import com.procsin.API.DAO.Pack.CampaignDao;
import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.API.Service.Interface.Pack.IISService;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.DB.Entity.ErrorLog;
import com.procsin.DB.Entity.Pack.Campaign;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Models.InvoiceResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderRepository;

    @Autowired
    private OrderLogDao orderLogRepository;

    @Autowired
    private CampaignDao campaignRepository;

    @Autowired
    private UserDao userRepository;

    @Autowired
    private IISService iisService;

    @Autowired
    private ErrorLogDao errorLogRepository;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    private void createErrorLog(String errorCode, String errorMessage) {
        ErrorLog errorLog = new ErrorLog(errorCode,errorMessage,getActiveUser().getId());
        errorLogRepository.save(errorLog);
    }

//    @Override
//    public OrderLogSuccessModel prepareOrder(OrderResponseModel orderModel) {
//        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
//        if (order == null) {
//            order = new Orders(orderModel);
//            orderRepository.save(order);
//        }
//
//        List<OrderLog> logs = orderLogRepository.findAllByOrder(order);
//        if (logs != null && logs.size() > 0) {
//            OrderLog lastLog = logs.get(logs.size() - 1);
//            if (lastLog != null) {
//                if (lastLog.getStatus() == OrderLog.OrderStatus.KARGO_HAZIR) {
//                    String errorCode = "Order-1001";
//                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
//                    createErrorLog(errorCode,errorMessage + " / " + order.getOrderCode());
//                    throw new IllegalStateException(errorMessage);
//                }
//                if (lastLog.getStatus() == OrderLog.OrderStatus.URUN_PAKETLENIYOR) {
//                    String errorCode = "Order-1002";
//                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce işleme alınmış";
//                    createErrorLog(errorCode,errorMessage + " / " + order.getOrderCode());
//                    throw new IllegalStateException(errorMessage);
//                }
//            }
//        }
//        OrderLog packLog = new OrderLog(order,OrderLog.OrderStatus.URUN_PAKETLENIYOR,getActiveUser());
//        orderLogRepository.save(packLog);
//
//        Campaign campaign = campaignRepository.findSuitableCampaign(order.getTotalCost());
//
////        order.addLogToList(packLog);
////        orderRepository.save(order);
//        return new OrderLogSuccessModel(true,"Başarılı",campaign);
//    }
//
//    @Override
//    public GenericResponse updateToSupplement(OrderResponseModel orderModel) {
//        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
//        if (order == null) {
//            String errorCode = "Order-1003";
//            String errorMessage = errorCode + "/" + "Bu sipariş hiç oluşturulmamış";
//            createErrorLog(errorCode,errorMessage + " / " + orderModel.OrderCode);
//            throw new IllegalStateException(errorMessage);
//        }
//        if (isPackedBefore(orderModel.OrderCode)) {
//            String errorCode = "Order-1004";
//            String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
//            createErrorLog(errorCode,errorMessage + " / " + orderModel.OrderCode);
//            throw new IllegalStateException(errorMessage);
//        }
//
//        OrderLog suppLog = new OrderLog(order, OrderLog.OrderStatus.TEDARIK_SURECINDE, getActiveUser());
//        orderLogRepository.save(suppLog);
////        order.addLogToList(suppLog);
//        return new GenericResponse(true,"Başarılı");
//    }

//    @Override
//    public GenericResponse updateToPackCancel(String orderCode) {
//        Orders order = orderRepository.findByOrderCode(orderCode);
//        if (order == null) {
//            String errorCode = "Order-1005";
//            String errorMessage = errorCode + "/" + "Bu sipariş hiç oluşturulmamış";
//            createErrorLog(errorCode,errorMessage + " / " + orderCode);
//            throw new IllegalStateException(errorMessage);
//        }
//        if (isPackedBefore(orderCode)) {
//            String errorCode = "Order-1006";
//            String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
//            createErrorLog(errorCode,errorMessage + " / " + orderCode);
//            throw new IllegalStateException(errorMessage);
//        }
//
//        OrderLog cancelLog = new OrderLog(order, OrderLog.OrderStatus.URUN_PAKETLENIYOR_IPTAL, getActiveUser());
//        orderLogRepository.save(cancelLog);
////        order.addLogToList(cancelLog);
////        orderRepository.save(order);
//        return new GenericResponse(true,"Başarılı");
//    }

//    @Override
//    public GenericResponse finishOrder(OrderResponseModel orderModel) {
//        if (isPackedBefore(orderModel.OrderCode)) {
//            String errorMessage = "Bu sipariş daha önce tamamlanmış";
//            createErrorLog("Order-1007",errorMessage + " / " + orderModel.OrderCode);
//            throw new IllegalStateException(errorMessage);
//        }
//        else {
//            Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
//            if (order == null) {
//                String errorMessage = "Bu sipariş hiç oluşturulmamış";
//                createErrorLog("Order-1008",errorMessage + " / " + orderModel.OrderCode);
//                throw new IllegalStateException(errorMessage);
//            }
//            OrderLog finishLog = new OrderLog(order, OrderLog.OrderStatus.KARGO_HAZIR, getActiveUser());
//            orderLogRepository.save(finishLog);
//
//            InvoiceResponseModel invoiceModel = iisService.createInvoice(orderModel.OrderCode);
//            updateOrderInvoiceInfo(orderModel.OrderCode, invoiceModel);
//            return new GenericResponse(true,"Başarılı");
//        }
//    }

//    @Override
//    public Boolean isPackedBefore(String orderCode) {
//        return orderLogRepository.findReadyByOrderCode(orderCode) != null;
//    }

//    @Override
//    public Orders updateOrderInvoiceInfo(String orderCode, InvoiceResponseModel responseModel) {
//        Orders orderModel = orderRepository.findByOrderCode(orderCode);
//        orderCode = " / " + orderCode;
//        ErrorLog errorLog = new ErrorLog();
//        errorLog.setDate(new Date());
//        errorLog.setUserId(getActiveUser().getId());
//        if (responseModel != null) {
//            if (orderModel != null) {
//                if (!responseModel.ExceptionMessage.isEmpty()) {
//                    errorLog.setErrorCode("Invoice-1001");
//                    errorLog.setErrorMessage(responseModel.ExceptionMessage + orderCode);
//                    errorLogRepository.save(errorLog);
//                } else {
//                    if (responseModel.EInvoiceNumber.isEmpty()) {
//                        errorLog.setErrorCode("Invoice-1002");
//                        errorLog.setErrorMessage("Faturası oluşturuldu, e-faturası oluşturulamadı." + orderCode);
//                        errorLogRepository.save(errorLog);
//                    }
//                }
//                orderModel.setInvoiceRefNumber(responseModel.InvoiceNumber);
//                orderModel.setInvoiceCode(responseModel.EInvoiceNumber);
//                return orderRepository.save(orderModel);
//            }
//            errorLog.setErrorCode("Invoice-1004");
//            errorLog.setErrorMessage("Fatura oluşturuldu, ilgili sipariş bulunamadığı için kendi tablomuza kaydedilemedi." + orderCode);
//            errorLogRepository.save(errorLog);
//        }
//        else {
//            errorLog.setErrorCode("Invoice-1003");
//            errorLog.setErrorMessage("Fatura servisi hata verdi." + orderCode);
//            errorLogRepository.save(errorLog);
//        }
//        return null;
//    }

//    @Override
//    public InvoiceResponseModel createInvoiceFromOrderCode(String orderCode) {
//        InvoiceResponseModel response = iisService.createInvoice(orderCode);
//        updateOrderInvoiceInfo(orderCode,response);
//        return response;
//    }

//    @Override
//    public Orders findByOrderCode(String orderCode) {
//        return orderRepository.findByOrderCode(orderCode);
//    }
//
//    @Override
//    public List<Orders> findLikeOrderCode(String orderCode) {
//        return orderRepository.findLikeOrderCode(orderCode);
//    }
//
//    @Override
//    public Orders changeFailedStatus(Boolean didFail, String orderCode) {
//        Orders order = findByOrderCode(orderCode);
//        order.setDidFail(didFail);
//        return orderRepository.save(order);
//    }

}
