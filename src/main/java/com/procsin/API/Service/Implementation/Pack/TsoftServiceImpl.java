package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.CampaignLogDao;
import com.procsin.API.DAO.ErrorLogDao;
import com.procsin.API.DAO.Pack.CampaignDao;
import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.API.Service.Interface.Pack.IISService;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.TsoftService;
import com.procsin.DB.Entity.ErrorLog;
import com.procsin.DB.Entity.Pack.Campaign;
import com.procsin.DB.Entity.Pack.CampaignLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Interfaces.TsoftInterface;
import com.procsin.Retrofit.Models.InvoiceResponseModel;
import com.procsin.Retrofit.Models.TSoft.OrderDataModel;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.ProductModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TsoftServiceImpl implements TsoftService {

    private OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    //getAccessToken is your own accessToken(retrieve it by saving in shared preference or any other option )
                        return chain.proceed(chain.request());
                }})
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build();

    private Retrofit tsoftRetrofit = new Retrofit.Builder()
            .baseUrl("http://www.procsin.com/rest1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(defaultHttpClient)
            .build();

    private TsoftInterface repo = tsoftRetrofit.create(TsoftInterface.class);

    @Autowired
    OrderService orderService;
    @Autowired
    IISService iisService;
    @Autowired
    OrderLogDao orderLogRepository;
    @Autowired
    OrderDao orderRepository;
    @Autowired
    ErrorLogDao errorLogRepository;
    @Autowired
    CampaignDao campaignRepository;
    @Autowired
    CampaignLogDao campaignLogRepository;
    @Autowired
    UserDao userRepository;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public OrderLogSuccessModel getSingleOrder(String token, boolean isTrendyol) {
        try {
            String trendyolSearchQuery = "OrderCode | TY | startswith";
            String tsoftSearchQuery = "OrderCode | TS | startswith";
            OrderDataModel response = repo.getOrders(token,"1204",true,5,"OrderDateTimeStamp ASC", isTrendyol ? trendyolSearchQuery : tsoftSearchQuery).execute().body();
            if (response != null) {
                if (response.data != null && response.data.size() > 0) {
//                    boolean isCompleted = false;
                    for (OrderModel model : response.data) {
                        OrderLogSuccessModel successModel = prepareOrder(token,model);
                        if (successModel.success) {
                            return successModel;
                        }
                    }
                }
                else if (response.success) {
                    return new OrderLogSuccessModel(true,"Sistemde bekleyen sipariş kalmadı.",null,null);
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public GenericResponse updateToSupplement(String token, String orderCode) {
        Orders order = orderRepository.findByOrderCode(orderCode);
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
            GenericTsoftResponseModel response = repo.updateOrderStatusToSupplement(token,updateOrderDataString(orderCode)).execute().body();
            if (response != null && response.success) {
                OrderLog suppLog = new OrderLog(order, OrderLog.OrderStatus.TEDARIK_SURECINDE, getActiveUser());
                orderLogRepository.save(suppLog);
                return new GenericResponse(true,"Başarılı");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GenericResponse(false,"Hata");
    }

    @Override
    public GenericResponse finishOrder(String token, String orderCode) {
        if (isPackedBefore(orderCode)) {
            String errorMessage = "Bu sipariş daha önce tamamlanmış";
            createErrorLog("Order-1007",errorMessage + " / " + orderCode);
            throw new IllegalStateException(errorMessage);
        }
        else {
            Orders order = orderRepository.findByOrderCode(orderCode);
            if (order == null) {
                String errorMessage = "Bu sipariş hiç oluşturulmamış";
                createErrorLog("Order-1008",errorMessage + " / " + orderCode);
                throw new IllegalStateException(errorMessage);
            }
            try {
                GenericTsoftResponseModel response = repo.updateOrderStatusToReady(token,updateOrderDataString(orderCode)).execute().body();
                if (response != null && response.success) {
                    OrderLog finishLog = new OrderLog(order, OrderLog.OrderStatus.KARGO_HAZIR, getActiveUser());
                    orderLogRepository.save(finishLog);
                    InvoiceResponseModel invoiceModel = iisService.createInvoice(orderCode);
                    updateOrderInvoiceInfo(orderCode, invoiceModel);
                    return new GenericResponse(true,"Başarılı");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String errorMessage = "Sipariş tamamlanırken bir sorun oluştu.";
        createErrorLog("Order-1009",errorMessage + " / " + orderCode);
        throw new IllegalStateException(errorMessage);
    }

    @Override
    public GenericResponse cancelOrder(String token, String orderCode) {
        Orders order = orderRepository.findByOrderCode(orderCode);
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
            GenericTsoftResponseModel response = repo.updateOrderStatusToPreparing(token, updateOrderDataString(orderCode)).execute().body();
            if (response != null && response.success) {
                OrderLog cancelLog = new OrderLog(order, OrderLog.OrderStatus.URUN_PAKETLENIYOR_IPTAL, getActiveUser());
                orderLogRepository.save(cancelLog);
                return new GenericResponse(true,"Başarılı");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String errorCode = "Order-1010";
        String errorMessage = errorCode + "/" + "sipariş iptal edilirken sorun oluştu.";
        createErrorLog(errorCode,errorMessage + " / " + orderCode);
        throw new IllegalStateException(errorMessage);
    }

    @Override
    public GenericResponse createInvoice(String orderCode) {
        InvoiceResponseModel invoiceModel = iisService.createInvoice(orderCode);
        return new GenericResponse(true,"Başarılı");
    }

    private OrderLogSuccessModel prepareOrder(String token, OrderModel orderModel) {
        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
        if (order == null) {
            orderModel.setTotalProductCount();
            order = new Orders(orderModel);
            orderRepository.save(order);
        }

        List<OrderLog> logs = orderLogRepository.findAllByOrder(order);
        if (logs != null && logs.size() > 0) {
            OrderLog lastLog = logs.get(logs.size() - 1);
            if (lastLog != null) {
                if (lastLog.getStatus() == OrderLog.OrderStatus.KARGO_HAZIR) {
                    String errorCode = "Order-1001";
                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce tamamlanmış";
                    createErrorLog(errorCode,errorMessage + " / " + order.getOrderCode());
                    return new OrderLogSuccessModel(false,"Hata",null,null);
                }
                if (lastLog.getStatus() == OrderLog.OrderStatus.URUN_PAKETLENIYOR) {
                    String errorCode = "Order-1002";
                    String errorMessage = errorCode + "/" + "Bu sipariş daha önce işleme alınmış";
                    createErrorLog(errorCode,errorMessage + " / " + order.getOrderCode());
                    return new OrderLogSuccessModel(false,"Hata",null,null);
                }
            }
        }

        try {
            OrderLog packLog = new OrderLog(order,OrderLog.OrderStatus.URUN_PAKETLENIYOR,getActiveUser());
            orderLogRepository.save(packLog);

            Campaign campaign = campaignRepository.findSuitableCampaign(order.getTotalCost());

            if (campaign != null) {
                boolean shouldShow = false;

                if (campaign.isIosAvailable() && orderModel.Application.equals("ios")) {
                    shouldShow = true;
                }
                else if (campaign.isAndroidAvailable() && orderModel.Application.equals("android")) {
                    shouldShow = true;
                }
                else if (campaign.isWebAvailable() && (orderModel.Application.equals("") || orderModel.Application.equals("mobile_site"))) {
                    shouldShow = true;
                }

                if (campaign.getCampaignType().equals("BASKET")) {
                    if (shouldShow) {
                        ProductModel model = new ProductModel();
                        model.Barcode = campaign.getProductBarcode();
                        model.ProductCode = campaign.getProductCode();
                        model.ProductName = campaign.getProductName();
                        model.Quantity = 1;
                        model.count = 1;
                        model.setTotalCount(1);
                        model.IsPackage = "0";
                        orderModel.OrderDetails.add(model);
                        CampaignLog campaignLog = new CampaignLog(campaign,new Date(),orderModel.OrderCode);
                        campaignLogRepository.save(campaignLog);
                    }
                    campaign = null;
                }
                else {
                    if (shouldShow) {
                        CampaignLog campaignLog = new CampaignLog(campaign,new Date(),orderModel.OrderCode);
                        campaignLogRepository.save(campaignLog);
                    }
                    else {
                        campaign = null;
                    }
                }
            }

            GenericTsoftResponseModel response = repo.updateOrderStatusToPack(token,updateOrderDataString(orderModel.OrderCode)).execute().body();
            if (response != null && response.success) {
                return new OrderLogSuccessModel(true,"Başarılı",campaign,orderModel);
            }
        } catch (IOException e) {
            return new OrderLogSuccessModel(false,"Hata",null,null);
        }
        return new OrderLogSuccessModel(false,"Hata",null,null);
    }

    private void createErrorLog(String errorCode, String errorMessage) {
        ErrorLog errorLog = new ErrorLog(errorCode,errorMessage,getActiveUser().getId());
        errorLogRepository.save(errorLog);
    }

    private Boolean isPackedBefore(String orderCode) {
        return orderLogRepository.findReadyByOrderCode(orderCode) != null;
    }

    private String updateOrderDataString(String orderCode) {
        return "[{\"OrderCode\":\"" + orderCode + "\"}]";
    }

    private Orders updateOrderInvoiceInfo(String orderCode, InvoiceResponseModel responseModel) {
        Orders orderModel = orderRepository.findByOrderCode(orderCode);
        orderCode = " / " + orderCode;
        ErrorLog errorLog = new ErrorLog();
        errorLog.setDate(new Date());
        errorLog.setUserId(getActiveUser().getId());
        if (responseModel != null) {
            if (orderModel != null) {
                if (responseModel.ExceptionMessage != null && !responseModel.ExceptionMessage.isEmpty()) {
                    errorLog.setErrorCode("Invoice-1001");
                    errorLog.setErrorMessage(responseModel.ExceptionMessage + orderCode);
                    errorLogRepository.save(errorLog);
                } else {
                    if (responseModel.EInvoiceNumber != null && (responseModel.EInvoiceNumber.isEmpty() || responseModel.EInvoiceNumber.equals("None"))) {
                        errorLog.setErrorCode("Invoice-1002");
                        errorLog.setErrorMessage("Faturası oluşturuldu, e-faturası oluşturulamadı." + orderCode);
                        errorLogRepository.save(errorLog);
                    }
                }
                orderModel.setInvoiceRefNumber(responseModel.InvoiceNumber);
                orderModel.setInvoiceCode(responseModel.EInvoiceNumber);
                return orderRepository.save(orderModel);
            }
            errorLog.setErrorCode("Invoice-1004");
            errorLog.setErrorMessage("Fatura oluşturuldu, ilgili sipariş bulunamadığı için kendi tablomuza kaydedilemedi." + orderCode);
            errorLogRepository.save(errorLog);
        }
        else {
            errorLog.setErrorCode("Invoice-1003");
            errorLog.setErrorMessage("Fatura servisi hata verdi." + orderCode);
            errorLogRepository.save(errorLog);
        }
        return null;
    }
}
