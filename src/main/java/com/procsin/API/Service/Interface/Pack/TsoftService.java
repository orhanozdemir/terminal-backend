package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.API.Model.TSOFT.LoginResponseDataModel;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.io.IOException;

public interface TsoftService {

    String getTsoftToken() throws IOException;
    OrderLogSuccessModel getTSoftOrder(String token) throws IOException;
    OrderModel getSingleOrder(String token, String orderCode) throws IOException;
    GenericTsoftResponseModel updateOrderStatus(String token, boolean isReturn, String orderCode, Orders.OrderStatusEnum status) throws IOException;
    GenericResponse createOrder(String token, String orderCode);
    OrderLogSuccessModel getReturnedOrder(String token) throws IOException;

}
