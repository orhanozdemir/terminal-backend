package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;

public interface TsoftService {

    OrderLogSuccessModel getSingleOrder(String token, boolean isTrendyol);
    GenericResponse updateToSupplement(String token, String orderCode);
    GenericResponse finishOrder(String token, String orderCode);
    GenericResponse cancelOrder(String token, String orderCode);

}
