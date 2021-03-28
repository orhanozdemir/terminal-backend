package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.RequestModel.ReturnOrderRequestModel;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderStatus;

import java.util.List;

public interface ReturnedOrderService {
    ReturnedOrder createReturnedOrder(ReturnOrderRequestModel requestModel);
    ReturnedOrder updateReturnedOrder(Long id, ReturnedOrderStatus status, String description, String trackingCode);

    List<ReturnedOrder> allReturnedOrders();
    List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrderStatus status);
    List<ReturnedOrder> waitingReturnedOrders();

    ReturnedOrder findReturnedOrder(String orderCode);

    void updateRequiredFields(String token);
}
