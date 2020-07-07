package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.CampaignLogDao;
import com.procsin.API.DAO.ErrorLogDao;
import com.procsin.API.DAO.Pack.CampaignDao;
import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.CreateOrderRequestModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.API.Service.Interface.Pack.*;
import com.procsin.DB.Entity.ErrorLog;
import com.procsin.DB.Entity.Pack.Campaign;
import com.procsin.DB.Entity.Pack.CampaignLog;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Interfaces.TsoftInterface;
import com.procsin.Retrofit.Models.TSoft.OrderDataModel;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.ProductModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    OrderLogService orderLogService;
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
    @Autowired
    EntityManager em;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public String getTsoftToken() throws IOException {
        return repo.login("prs300377").execute().body().data.get(0).token;
    }

    @Override
    public OrderLogSuccessModel getTSoftOrder(String token) throws IOException {
        OrderDataModel response = getOrderByStatus(token, "1204");
        return handleOrderResponse(token, response);
    }

    @Override
    public OrderModel getSingleOrder(String token, String orderCode) throws IOException {
        OrderDataModel response = null;
        response = repo.getSingleOrder(token,true,orderCode,"0").execute().body();
        if (response != null) {
            if (response.data == null || response.data.size() == 0) {
                response = repo.getSingleOrder(token,true,orderCode,"1").execute().body();
            }
            if (response != null && response.data != null && response.data.size() > 0) {
                return response.data.get(0);
            }
        }
        System.out.println(orderCode);
        throw new IllegalArgumentException();
    }

    @Override
    public GenericTsoftResponseModel updateOrderStatus(String token, String orderCode, Orders.OrderStatusEnum status) throws IOException {
        switch (status) {
            case CANCELED:
                return repo.updateOrderStatusToPreparing(token, updateOrderDataString(orderCode)).execute().body();
            case PACKING:
                return repo.updateOrderStatusToPack(token, updateOrderDataString(orderCode)).execute().body();
            case PACKED:
                return repo.updateOrderStatusToReady(token,updateOrderDataString(orderCode)).execute().body();
            case NEED_SUPPLY:
                return repo.updateOrderStatusToSupplement(token,updateOrderDataString(orderCode)).execute().body();
        }
        return null;
    }

    @Override
    public GenericResponse createOrder(String token, String orderCode) {
        OrderModel existingOrder = null;

        String[] codes = orderCode.split(",");

        for (String code : codes) {
            try {
                existingOrder = getSingleOrder(token,code);
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
            CreateOrderRequestModel requestModel = new CreateOrderRequestModel(existingOrder);
            String data = requestModel.generatePostDataString().replace("\n","").replace("\r","");
            try {
                repo.createOrder(token,data).execute();
            } catch (IOException e) {
                return new GenericResponse(false, code);
            }
//            return new GenericResponse(true, code);
        }
        return new GenericResponse(false, orderCode);
    }

    @Override
    public OrderLogSuccessModel getReturnedOrder(String token) throws IOException {
        OrderDataModel response = getOrderByStatus(token, "1206");
        return handleOrderResponse(token,response);
    }

    private OrderDataModel getOrderByStatus(String token, String statusId) throws IOException {
        String tsoftSearchQuery = "OrderCode | TS | startswith";
        String tsoftSortQuery = "OrderDateTimeStamp ASC";
        return repo.getOrders(token,statusId,true,1, tsoftSortQuery, tsoftSearchQuery).execute().body();
    }

    private OrderLogSuccessModel handleOrderResponse(String token, OrderDataModel response) {
        if (response != null) {
            if (response.data != null && response.data.size() > 0) {
                for (OrderModel model : response.data) {
                    OrderLogSuccessModel successModel = prepareTsoftOrder(token,model);
                    if (successModel.success) {
                        return successModel;
                    }
                }
            }
            else if (response.success) {
                return new OrderLogSuccessModel(true,"Sistemde bekleyen sipariş kalmadı.",null,null);
            }
        }
        throw new IllegalArgumentException();
    }

    private OrderLogSuccessModel prepareTsoftOrder(String token, OrderModel orderModel) {
        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);

        refundCheck(orderModel);

        if (order == null) {
//            orderModel.setTotalProductCount();
            order = new Orders(orderModel);
            orderRepository.save(order);
        }

        boolean isAvailable = orderService.isOrderAvailable(order.getOrderCode());
        if (!isAvailable) {
            return new OrderLogSuccessModel(false,"Hata",null,null);
        }

        try {
            orderLogService.createOrderLog(order,OrderLog.OrderStatus.URUN_PAKETLENIYOR);

            Date date = new Date((orderModel.OrderDateTimeStamp + 10800) * 1000);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String orderDate = dateFormat.format(date);

            Campaign campaign = campaignRepository.findSuitableCampaign(order.getTotalCost(), orderDate);

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

//            GenericTsoftResponseModel response = repo.updateOrderStatusToPack(token,updateOrderDataString(orderModel.OrderCode)).execute().body();
            GenericTsoftResponseModel response = updateOrderStatus(token,orderModel.OrderCode,Orders.OrderStatusEnum.PACKING);
            if (response != null && response.success) {
                orderModel.DeliveryName = orderModel.DeliveryName + " - " + orderModel.Cargo;
                return new OrderLogSuccessModel(true,"Başarılı",campaign,orderModel);
            }
        } catch (IOException e) {
            return new OrderLogSuccessModel(false,"Hata",null,null);
        }
        return new OrderLogSuccessModel(false,"Hata",null,null);
    }

    private void refundCheck(OrderModel orderModel) {
        int tempTotal = 0;
        for (ProductModel model : new ArrayList<ProductModel>(orderModel.OrderDetails)) {
            if (model.IsPackage.equals("1")) {
                for (ProductModel innerModel : model.PackageContent) {
                    innerModel.Quantity = innerModel.Quantity - innerModel.RefundCount;
                    if (innerModel.count == null) {
                        innerModel.count = model.Quantity;
                        innerModel.setTotalCount(innerModel.count);
                        tempTotal += innerModel.count;
                    }
                    if (innerModel.Quantity == 0) {
                        orderModel.OrderDetails.remove(model);
                    }
                }
            }
            else {
                model.Quantity = model.Quantity - model.RefundCount;
                if (model.count == null) {
                    model.count = model.Quantity;
                    model.setTotalCount(model.count);
                    tempTotal += model.count;
                }
                if (model.Quantity == 0) {
                    orderModel.OrderDetails.remove(model);
                }
            }
        }
        orderModel.totalProductCount = tempTotal;
    }

    private void createErrorLog(String errorCode, String errorMessage) {
        ErrorLog errorLog = new ErrorLog(errorCode,errorMessage,getActiveUser().getId());
        errorLogRepository.save(errorLog);
    }

    private String updateOrderDataString(String orderCode) {
        return "[{\"OrderCode\":\"" + orderCode + "\"}]";
    }
}