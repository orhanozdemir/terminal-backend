package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

public interface OrderService {

    OrderLogSuccessModel getSingleOrder(String token, boolean isTrendyol);
    OrderModel getSpecificOrder(String token, String orderCode);
    GenericResponse finishOrder(String token, String orderCode);
    GenericResponse updateToSupplement(String token, String orderCode);
    GenericResponse cancelOrder(String token, String orderCode);

    boolean isOrderAvailable(String orderCode);
    boolean isPackedBefore(String orderCode);

}
