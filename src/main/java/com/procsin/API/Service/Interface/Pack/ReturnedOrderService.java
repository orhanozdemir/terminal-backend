package com.procsin.API.Service.Interface.Pack;

import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;

import java.util.List;

public interface ReturnedOrderService {
    ReturnedOrder createReturnedOrder(ReturnedOrder returnedOrder);
    ReturnedOrder updateReturnedOrder(Long id, ReturnedOrder.ReturnedOrderStatus status);
    ReturnedOrderLog changeReturnedOrderStatus(Long id, ReturnedOrder.ReturnedOrderStatus status);

    List<ReturnedOrder> allReturnedOrders();
    List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrder.ReturnedOrderStatus status);
    List<ReturnedOrder> waitingReturnedOrders();
}
