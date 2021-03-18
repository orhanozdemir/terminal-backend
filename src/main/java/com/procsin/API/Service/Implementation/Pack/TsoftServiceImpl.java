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
import com.procsin.Retrofit.Models.TSoft.StatusEnum;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import javax.persistence.EntityManager;
import javax.persistence.ValidationMode;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
    public OrderLogSuccessModel getTSoftOrder(String token, boolean isTrendyol) throws IOException {
        OrderDataModel response = getOrderByStatus(token, StatusEnum.URUN_HAZIRLANIYOR, isTrendyol);
        return handleOrderResponse(token,false,response);
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
                sortProductArray(response.data.get(0));
                return response.data.get(0);
            }
        }

        System.out.println(orderCode);
        throw new IllegalArgumentException();
    }

    void sortProductArray(OrderModel model) {
        Collections.sort(model.OrderDetails, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                return o1.ProductCode.compareTo(o2.ProductCode);
            }
        });
    }

    @Override
    public GenericTsoftResponseModel updateOrderStatus(String token, boolean isReturn, String orderCode, Orders.OrderStatusEnum status) throws IOException {
        switch (status) {
            case CANCELED:
                if (isReturn) {
                    return repo.updateOrderStatusToReturnPreparing(token, updateOrderDataString(orderCode)).execute().body();
                }
                return repo.updateOrderStatusToPreparing(token, updateOrderDataString(orderCode)).execute().body();
            case PACKING:
                return repo.updateOrderStatusToPack(token, updateOrderDataString(orderCode)).execute().body();
            case PACKED:
                return repo.updateOrderStatusToReady(token,updateOrderDataString(orderCode)).execute().body();
            case NEED_SUPPLY:
                if (isReturn) {
                    return repo.updateOrderStatusToReturnSupplement(token, updateOrderDataString(orderCode)).execute().body();
                }
                return repo.updateOrderStatusToSupplement(token,updateOrderDataString(orderCode)).execute().body();
        }
        return null;
    }

    @Override
    public GenericResponse createOrder(String token, String orderCode) {
        OrderModel existingOrder = null;

        if (token == null) {
            try {
                token = getTsoftToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        }
        return new GenericResponse(false, orderCode);
    }

    @Override
    public OrderLogSuccessModel getReturnedOrder(String token) throws IOException {
        OrderDataModel response = getOrderByStatus(token, StatusEnum.CIKIS_BEKLENIYOR, false);
        return handleOrderResponse(token,true,response);
    }

    @Override
    public List<OrderModel> getAllOrdersBetweenDates(String start, String end) throws IOException {
        String token = getTsoftToken();
        List<OrderModel> allOrders = new ArrayList<>();
        String tsoftSearchQuery = "OrderCode | TS | startswith";
        int limit = 500;
        String tsoftSortQuery = "OrderDateTimeStamp ASC";

        long startMillis = new DateTime(start).getMillis()/1000;
        long endMillis = new DateTime(end).getMillis()/1000;

        OrderDataModel response = repo.getOrdersWithDate(token, true, startMillis, endMillis,
                limit, 0, tsoftSortQuery, tsoftSearchQuery).execute().body();
        if (response != null) {
            allOrders.addAll(response.data);
            int totalOrderCount = response.summary.totalRecordCount;
            int totalPage = (totalOrderCount%limit == 0) ? (totalOrderCount / limit) : (totalOrderCount / limit + 1);
            for (int i = 1; i < totalPage; i++) {
                OrderDataModel temp = repo.getOrdersWithDate(token, true, startMillis, endMillis,
                        limit, limit*i, tsoftSortQuery, tsoftSearchQuery).execute().body();
                if(temp != null)
                    allOrders.addAll(temp.data);
            }
        }
        return allOrders;
    }

    @Override
    public void calculateBasketCount(List<OrderModel> orders) throws IOException {
        Map<String,Integer> baskets = new HashMap<>();
        for (OrderModel order : orders) {
            sortProductArray(order);
            String orderStr = "";
            for (ProductModel product : order.OrderDetails) {
                orderStr += product.ProductName + "-" + product.Quantity + ",";
            }
            orderStr = orderStr.substring(0, orderStr.length() - 1);

            if (baskets.containsKey(orderStr)) {
                int value = baskets.get(orderStr);
                baskets.put(orderStr, value + 1);
            } else {
                baskets.put(orderStr, 1);
            }
        }

        mapToExcel("sepetler","sepetler",baskets);
    }

    @Override
    public void productQuantitiesInOrders(List<OrderModel> orderModels) throws IOException {
        Map<String, Integer> quantityMap = new HashMap<>();
        for (OrderModel orderModel : orderModels) {
            for (ProductModel product : orderModel.OrderDetails) {
                if (quantityMap.containsKey(product.ProductCode)) {
                    int value = quantityMap.get(product.ProductCode);
                    quantityMap.put(product.ProductCode, (value + product.Quantity));
                } else {
                    quantityMap.put(product.ProductCode, product.Quantity);
                }
            }
        }
        mapToExcel("kampanya-adet","Kampanya - Ürün Adetleri",quantityMap);
    }

    public void mapToExcel(String fileName, String sheetTitle, Map<String,Integer> map) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        int rowCount = 0;

        for (String key : map.keySet()) {
            Row row = sheet.createRow(++rowCount);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(key);
            Cell cell2 = row.createCell(1);
            cell2.setCellValue(map.get(key));
        }

        try (FileOutputStream outputStream = new FileOutputStream( fileName+".xlsx")) {
            workbook.write(outputStream);
        }
    }

    @Override
    public List<OrderModel> getAllOrdersByStatus(StatusEnum status) throws IOException {
        String token = getTsoftToken();
        List<OrderModel> allOrders = new ArrayList<>();
        String tsoftSearchQuery = "OrderCode | TS | startswith";

        int limit = 500;
        String tsoftSortQuery = "OrderDateTimeStamp ASC";
        OrderDataModel response = repo.getOrders(token,status.statusId,true,limit,
                0, tsoftSortQuery, tsoftSearchQuery).execute().body();
        if(response != null) {
            allOrders.addAll(response.data);
            int totalOrderCount = response.summary.totalRecordCount;
            int totalPage = (totalOrderCount%limit == 0) ? (totalOrderCount / limit) : (totalOrderCount / limit + 1);
            for (int i = 1; i < totalPage; i++) {
                OrderDataModel temp = repo.getOrders(token, status.statusId, true, limit,
                        limit*i, tsoftSortQuery, tsoftSearchQuery).execute().body();
                if(temp != null)
                    allOrders.addAll(temp.data);
            }
        }
        return allOrders;
    }

    @Override
    public List<OrderModel> filterOrdersByProducts(List<OrderModel> orders, List<String> withProducts, List<String> withoutProducts) {
        List<OrderModel> filteredOrders = new ArrayList<>();
        for (OrderModel order : orders) {
            boolean wantedFlag = false;
            boolean unwantedFlag = false;
            for (ProductModel product : order.OrderDetails) {
                if (withoutProducts.contains(product.ProductCode)) {
                    unwantedFlag = true;
                    break;
                }
                if (withProducts.size() == 0 || withProducts.contains(product.ProductCode)) {
                    wantedFlag = true;
                }
            }
            if (wantedFlag && !unwantedFlag) {
                filteredOrders.add(order);
            }
        }
        return filteredOrders;
    }

    @Override
    public GenericResponse updateStatuses(List<OrderModel> orders, StatusEnum newStatus) throws IOException {
        int period = 50;
        int pageCount = (orders.size() % period) == 0 ? (orders.size() / period) : (orders.size() / period + 1);
        String token = getTsoftToken();
        for (int i = 0; i < pageCount; i++) {
            int startIndex = i * period;
            int endIndex = Math.min((startIndex + period), orders.size());
            List<OrderModel> tempOrders = orders.subList(startIndex, endIndex);
            String data = "[";
            for (OrderModel temp : tempOrders) {
                data += "{\"OrderCode\":\"" + temp.OrderCode + "\"},";
            }
            data = data.substring(0, data.length() - 1);
            data += "]";
            switch (newStatus) {
                case URUN_HAZIRLANIYOR:
                    repo.updateOrderStatusToPreparing(token,data).execute();
                    break;
                case TEDARIK_SURECINDE:
                    repo.updateOrderStatusToSupplement(token,data).execute();
                    break;
                case CIKIS_BEKLENIYOR:
                    repo.updateOrderStatusToReturnPreparing(token,data).execute();
                    break;
                case YENIDEN_CIKIS_TEDARIK:
                    repo.updateOrderStatusToReturnSupplement(token,data).execute();
                    break;
                default:
                    break;
            }
        }
        return new GenericResponse(true, "Başarılı");
    }

    private List<OrderModel> getAllWaitingOrders() throws IOException {
        String token = getTsoftToken();
        List<OrderModel> allOrders = new ArrayList<>();
        String tsoftSearchQuery = "OrderCode | TS | startswith";
        int limit = 500;
        String tsoftSortQuery = "OrderDateTimeStamp ASC";
        OrderDataModel response = repo.getOrders(token,"1204",true,limit,
                0, tsoftSortQuery, tsoftSearchQuery).execute().body();

        if(response != null) {
            allOrders.addAll(response.data);
            int totalOrderCount = response.summary.totalRecordCount;
            int totalPage = (totalOrderCount%limit == 0) ? (totalOrderCount / limit) : (totalOrderCount / limit + 1);
            for (int i = 1; i < totalPage; i++) {
                OrderDataModel temp = repo.getOrders(token, "1204", true, limit,
                        limit*i, tsoftSortQuery, tsoftSearchQuery).execute().body();
                allOrders.addAll(temp.data);
            }
        }

        return allOrders;
    }
  
    private OrderDataModel getOrderByStatus(String token, StatusEnum status, boolean isTrendyol) throws IOException {
        String searchQuery = "OrderCode | " + (isTrendyol ? "TY" : "TS") + " | startswith";
        String sortQuery = "OrderDateTimeStamp ASC";
        return repo.getOrders(token,status.statusId,true,1,0, sortQuery, searchQuery).execute().body();
    }

    private OrderLogSuccessModel handleOrderResponse(String token, boolean isReturn, OrderDataModel response) {
        if (response != null) {
            if (response.data != null && response.data.size() > 0) {
                for (OrderModel model : response.data) {
                    sortProductArray(model);
                    OrderLogSuccessModel successModel = prepareTsoftOrder(token,isReturn,model);
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

    private OrderLogSuccessModel prepareTsoftOrder(String token, boolean isReturn, OrderModel orderModel) {
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

            Date date = new Date((orderModel.OrderDateTimeStamp) * 1000);
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
            GenericTsoftResponseModel response = updateOrderStatus(token,isReturn,orderModel.OrderCode,Orders.OrderStatusEnum.PACKING);
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