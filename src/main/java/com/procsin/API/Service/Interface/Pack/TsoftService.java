package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.io.IOException;

public interface TsoftService {

    OrderLogSuccessModel getTSoftOrder(String token) throws IOException;
    OrderModel getSingleOrder(String token, String orderCode) throws IOException;
    GenericTsoftResponseModel updateOrderStatus(String token, String orderCode, Orders.OrderStatusEnum status) throws IOException;

}
