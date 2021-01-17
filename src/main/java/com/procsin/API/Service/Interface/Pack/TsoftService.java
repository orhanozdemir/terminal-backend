package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.StatusEnum;
import java.io.IOException;
import java.util.List;

public interface TsoftService {

    String getTsoftToken() throws IOException;
    OrderLogSuccessModel getTSoftOrder(String token, boolean isTrendyol) throws IOException;
    OrderModel getSingleOrder(String token, String orderCode) throws IOException;
    GenericTsoftResponseModel updateOrderStatus(String token, boolean isReturn, String orderCode, Orders.OrderStatusEnum status) throws IOException;
    GenericResponse createOrder(String token, String orderCode);
    OrderLogSuccessModel getReturnedOrder(String token) throws IOException;

    List<OrderModel> getAllOrdersByStatus(StatusEnum status) throws IOException;
    List<OrderModel> filterOrdersByProducts(List<OrderModel> orders, List<String> withProducts, List<String> withoutProducts);
    GenericResponse updateStatuses(List<OrderModel> orders, StatusEnum newStatus) throws IOException;
}
