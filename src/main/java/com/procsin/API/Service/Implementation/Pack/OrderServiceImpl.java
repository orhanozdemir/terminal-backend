package com.procsin.API.Service.Implementation.Pack;

import com.procsin.API.DAO.Pack.CampaignDao;
import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.DAO.UserDao;
import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.OrderLogSuccessModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.API.Service.Interface.Pack.OrderService;
import com.procsin.Application;
import com.procsin.DB.Entity.Pack.Campaign;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.DB.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderRepository;

    @Autowired
    private OrderLogDao orderLogRepository;

    @Autowired
    private CampaignDao campaignRepository;

    @Autowired
    private UserDao userRepository;

    private User getActiveUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername());
    }

    @Override
    public OrderLogSuccessModel prepareOrder(OrderResponseModel orderModel) {
        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
        if (order == null) {
            order = new Orders(orderModel);
            orderRepository.save(order);
        }

        List<OrderLog> logs = orderLogRepository.findAllByOrder(order);
        if (logs != null && logs.size() > 0) {
            OrderLog lastLog = logs.get(logs.size() - 1);
            if (lastLog != null) {
                if (lastLog.getStatus() == OrderLog.OrderStatus.KARGO_HAZIR) {
                    throw new IllegalStateException("Bu sipariş daha önce tamamlanmış");
                }
                if (lastLog.getStatus() == OrderLog.OrderStatus.URUN_PAKETLENIYOR) {
                    throw new IllegalStateException("Bu sipariş daha önce işleme alınmış");
                }
            }
        }
        OrderLog packLog = new OrderLog(order,OrderLog.OrderStatus.URUN_PAKETLENIYOR,getActiveUser());
        orderLogRepository.save(packLog);

        Campaign campaign = campaignRepository.findSuitableCampaign(order.getTotalCost());

//        order.addLogToList(packLog);
//        orderRepository.save(order);
        return new OrderLogSuccessModel(true,"Başarılı",campaign);
    }

    @Override
    public GenericResponse updateToSupplement(OrderResponseModel orderModel) {
        Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
        if (order == null) {
            throw new IllegalStateException("Bu sipariş hiç oluşturulmamış");
        }
        if (isPackedBefore(orderModel.OrderCode)) {
            throw new IllegalStateException("Bu sipariş daha önce tamamlanmış");
        }

        OrderLog suppLog = new OrderLog(order, OrderLog.OrderStatus.TEDARIK_SURECINDE, getActiveUser());
        orderLogRepository.save(suppLog);
//        order.addLogToList(suppLog);
        return new GenericResponse(true,"Başarılı");
    }

    @Override
    public GenericResponse updateToPackCancel(String orderCode) {
        Orders order = orderRepository.findByOrderCode(orderCode);
        if (order == null) {
            throw new IllegalStateException("Bu sipariş hiç oluşturulmamış");
        }
        if (isPackedBefore(orderCode)) {
            throw new IllegalStateException("Bu sipariş daha önce tamamlanmış");
        }

        OrderLog cancelLog = new OrderLog(order, OrderLog.OrderStatus.URUN_PAKETLENIYOR_IPTAL, getActiveUser());
        orderLogRepository.save(cancelLog);
//        order.addLogToList(cancelLog);
//        orderRepository.save(order);
        return new GenericResponse(true,"Başarılı");
    }

    @Override
    public GenericResponse finishOrder(OrderResponseModel orderModel) {
        if (isPackedBefore(orderModel.OrderCode)) {
            throw new IllegalStateException("Bu sipariş daha önce tamamlanmış");
        }
        else {
            Orders order = orderRepository.findByOrderCode(orderModel.OrderCode);
            if (order == null) {
                throw new IllegalStateException("Bu sipariş hiç oluşturulmamış");
            }
            OrderLog finishLog = new OrderLog(order, OrderLog.OrderStatus.KARGO_HAZIR, getActiveUser());
            orderLogRepository.save(finishLog);
            return new GenericResponse(true,"Başarılı");
        }
    }

    @Override
    public Boolean isPackedBefore(String orderCode) {
        return orderLogRepository.findReadyByOrderCode(orderCode) != null;
    }

}
