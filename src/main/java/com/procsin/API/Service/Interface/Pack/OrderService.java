package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.io.IOException;

public interface OrderService {

    OrderLogSuccessModel getSingleOrder(String token, boolean isTrendyol);
    OrderModel getSpecificOrder(String token, String orderCode);
    OrderModel searchOrder(String token, String orderCode) throws IOException;
    GenericResponse finishOrder(String token, boolean isReturn, String orderCode);
    GenericResponse updateToSupplement(String token, boolean isReturn, String orderCode);
    GenericResponse cancelOrder(String token, boolean isReturn, String orderCode);

    OrderLogSuccessModel getReturnedOrder(String token);

    boolean isOrderAvailable(String orderCode);
    boolean isPackedBefore(String orderCode);

}
