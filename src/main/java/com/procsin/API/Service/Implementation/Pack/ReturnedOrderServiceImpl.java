package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.Return.ReturnedOrderDAO;
import com.procsin.API.DAO.Pack.Return.ReturnedOrderLogDAO;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Service.Interface.Pack.ReturnedOrderService;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrder;
import com.procsin.DB.Entity.Pack.Return.ReturnedOrderLog;
import com.procsin.DB.Entity.UserManagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReturnedOrderServiceImpl implements ReturnedOrderService {

    @Autowired
    UserDao userRepository;

    @Autowired
    ReturnedOrderDAO returnedOrderDAO;

    @Autowired
    ReturnedOrderLogDAO returnedOrderLogDAO;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public ReturnedOrder createReturnedOrder(ReturnedOrder returnedOrder) {
        returnedOrder.createdAt = new Date();
        returnedOrder.createdBy = getActiveUser();

        return returnedOrderDAO.save(returnedOrder);
    }

    @Override
    public ReturnedOrder updateReturnedOrder(Long id, ReturnedOrder.ReturnedOrderStatus status) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findById(id).isPresent() ? returnedOrderDAO.findById(id).get() : null;
        if (returnedOrder != null) {
            returnedOrder.status = status;
            returnedOrder.lastUpdatedAt = new Date();
            returnedOrder.lastUpdatedBy = getActiveUser();

            return returnedOrderDAO.save(returnedOrder);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public ReturnedOrderLog changeReturnedOrderStatus(Long id, ReturnedOrder.ReturnedOrderStatus status) {
        ReturnedOrder returnedOrder = returnedOrderDAO.findById(id).isPresent() ? returnedOrderDAO.findById(id).get() : null;
        ReturnedOrderLog log = new ReturnedOrderLog(returnedOrder,status,getActiveUser());
        returnedOrderLogDAO.save(log);
        updateReturnedOrder(id,status);
        return log;
    }

    @Override
    public List<ReturnedOrder> allReturnedOrders() {
        List<ReturnedOrder> allOrders = new ArrayList<>();
        returnedOrderDAO.findAll().iterator().forEachRemaining(allOrders::add);
        return allOrders;
    }

    @Override
    public List<ReturnedOrder> returnedOrdersByStatus(ReturnedOrder.ReturnedOrderStatus status) {
        List<ReturnedOrder> returnedOrders = returnedOrderDAO.findAllByStatus(status);
        return returnedOrders;
    }

    @Override
    public List<ReturnedOrder> waitingReturnedOrders() {
        return returnedOrdersByStatus(ReturnedOrder.ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR);
    }
}
