package com.procsin.API.Service.Interface.Pack;

import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.UserManagement.User;

public interface OrderLogService {

    OrderLog createOrderLog(Orders order, OrderLog.OrderStatus status);

//    void updateOrderStatus(String orderCode, Orders.OrderStatusEnum status);

}
