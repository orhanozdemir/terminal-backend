package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.RequestModel.GetBasketOrders.GetBasketOrdersRequestModel;
import com.procsin.API.Model.RequestModel.GetBasketOrders.GetBasketTypesResponseModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.StatusEnum;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TsoftService {

    String getTsoftToken() throws IOException;
    OrderLogSuccessModel getTSoftOrder(String token, StatusEnum status, boolean isTrendyol) throws IOException;
    OrderModel getSingleOrder(String token, String orderCode) throws IOException;
    OrderModel searchOrder(String token, String orderCode) throws IOException;
    GenericTsoftResponseModel updateOrderStatus(String token, boolean isReturn, String orderCode, Orders.OrderStatusEnum status) throws IOException;
    GenericResponse createOrder(String token, String orderCode);
    OrderLogSuccessModel getReturnedOrder(String token) throws IOException;
    List<OrderModel> getAllOrdersBetweenDates(String start, String end) throws IOException;
    List<OrderModel> getAllOrdersByStatus(StatusEnum status) throws IOException;
    List<OrderModel> getBasketOrders(GetBasketOrdersRequestModel requestModel) throws IOException;
    void updateOrdersWithBasketStr(String basketStr) throws IOException;
    List<GetBasketTypesResponseModel> getBasketTypes(StatusEnum statusEnum) throws IOException;
    void productQuantitiesInOrders(List<OrderModel> orderModels) throws IOException;
    Map<String,Integer> calculateBasketCount(List<OrderModel> orders, boolean shouldCreateExcel) throws IOException;
    List<OrderModel> filterOrdersByProducts(List<OrderModel> orders, List<String> withProducts, List<String> withoutProducts);
    GenericResponse updateStatuses(List<OrderModel> orders, StatusEnum newStatus) throws IOException;
}