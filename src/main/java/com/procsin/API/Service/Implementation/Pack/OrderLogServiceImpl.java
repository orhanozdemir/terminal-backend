package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Service.Interface.Pack.OrderLogService;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    OrderLogDao orderLogRepository;

    @Autowired
    UserDao userRepository;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public OrderLog createOrderLog(Orders order, OrderLog.OrderStatus status) {
        OrderLog suppLog = new OrderLog(order, status, getActiveUser());
        return orderLogRepository.save(suppLog);
    }

//    @Override
//    public void updateOrderStatus(String orderCode, Orders.OrderStatusEnum status) {
////        orderLogRepository.updateOrderStatus("PACKING",orderCode);
//    }
}
