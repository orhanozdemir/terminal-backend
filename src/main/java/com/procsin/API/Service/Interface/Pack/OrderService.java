package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;

public interface OrderService {

    OrderLogSuccessModel prepareOrder(OrderResponseModel orderModel);
    GenericResponse updateToSupplement(OrderResponseModel orderModel);
    GenericResponse updateToPackCancel(String orderCode);
    GenericResponse finishOrder(OrderResponseModel orderModel);
    Boolean isPackedBefore(String orderCode);
}
