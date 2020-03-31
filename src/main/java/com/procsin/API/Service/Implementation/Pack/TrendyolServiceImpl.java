package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Service.Interface.Pack.OrderLogService;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.API.Service.Interface.Pack.TrendyolService;
import com.procsin.Configuration.BasicAuthInterceptor;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.TrendyolAPI.Interface.TrendyolInterface;
import com.procsin.TrendyolAPI.Model.TrendyolOrdersResponseModel;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TrendyolServiceImpl implements TrendyolService {

    private String trendyolBaseURL = "https://api.trendyol.com/";
    public static long supplierID = 104907;
    private String trendyolAPIUsername = "RYVF7bmpIL1HMLl4KToQ";
    private String trendyolAPIPassword = "F2IovBdYqIsANeNYGInW";

    private OkHttpClient trendyolHTTPClient = new OkHttpClient.Builder()
            .addInterceptor(new BasicAuthInterceptor(trendyolAPIUsername,trendyolAPIPassword))
            .callTimeout(300, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .connectTimeout(300, TimeUnit.SECONDS)
            .build();

    private Retrofit trendyolRetrofit = new Retrofit.Builder()
            .baseUrl(trendyolBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(trendyolHTTPClient)
            .build();

    private TrendyolInterface trendyolRepo = trendyolRetrofit.create(TrendyolInterface.class);

    @Autowired
    OrderLogService orderLogService;
    @Autowired
    OrderService orderService;

    @Autowired
    EntityManager em;

    @Override
    public OrderLogSuccessModel getOrder() {
        String getOrderQuery = "SELECT TOP 1 * FROM PRS_SEVK.sevk.Orders WHERE (status IS NULL OR status = 'CREATED') ORDER BY orderDate";
        Orders orderResponse = (Orders) em.createNativeQuery(getOrderQuery,Orders.class).getSingleResult();
        if (orderResponse != null) {
            boolean isAvailable = orderService.isOrderAvailable(orderResponse.getOrderCode());
            if (!isAvailable) {
                return new OrderLogSuccessModel(false,"",null,null);
            }

//            orderLogService.updateOrderStatus(orderResponse.getOrderCode(), Orders.OrderStatusEnum.PACKING);
            orderLogService.createOrderLog(orderResponse,OrderLog.OrderStatus.URUN_PAKETLENIYOR);

//            Update trendyol status ??

            String trendyolOrderNumber = orderResponse.getOrderCode().substring(2);
            try {
                TrendyolOrdersResponseModel ordersResponse = trendyolRepo.getSingleOrder(supplierID,trendyolOrderNumber).execute().body();
                if (ordersResponse != null && ordersResponse.content.size() > 0) {
                    OrderModel terminalOrder = new OrderModel(ordersResponse.content.get(0));
                    return new OrderLogSuccessModel(true,"",null,terminalOrder);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new OrderLogSuccessModel(false,"",null,null);
    }
}
