package com.procsin.API.Service.Interface.Pack;

import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.util.List;

public interface ReturnedOrderService {
    ReturnedOrder createReturnedOrder(OrderModel orderModel);
    ReturnedOrder updateReturnedOrder(Long id, ReturnedOrder.ReturnedOrderStatus status, String description, String trackingCode);
    ReturnedOrderLog changeReturnedOrderStatus(Long id, ReturnedOrder.ReturnedOrderStatus status);

    List<ReturnedOrder> allReturnedOrders();
    List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrder.ReturnedOrderStatus status);
    List<ReturnedOrder> waitingReturnedOrders();
}
