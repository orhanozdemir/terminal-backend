package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.ReturnOrderRequestModel;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.io.IOException;
import java.util.List;

public interface ReturnedOrderService {
    ReturnedOrder createReturnedOrder(ReturnOrderRequestModel requestModel);
    ReturnedOrder updateReturnedOrder(Long id, ReturnedOrder.ReturnedOrderStatus status, String description, String trackingCode);
    ReturnedOrderLog changeReturnedOrderStatus(Long id, ReturnedOrder.ReturnedOrderStatus status);

    List<ReturnedOrder> allReturnedOrders();
    List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrder.ReturnedOrderStatus status);
    List<ReturnedOrder> waitingReturnedOrders();

    void updateRequiredFields(String token);
}
