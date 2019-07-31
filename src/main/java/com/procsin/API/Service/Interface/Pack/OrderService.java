package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.DB.Entity.User;

public interface OrderService {

    OrderLogSuccessModel prepareOrder(User user, OrderResponseModel orderModel);
    GenericResponse updateToSupplement(User user, OrderResponseModel orderModel);
    GenericResponse updateToPackCancel(User user, String orderCode);
    GenericResponse finishOrder(User user, OrderResponseModel orderModel);
    Boolean isPackedBefore(String orderCode);

}
