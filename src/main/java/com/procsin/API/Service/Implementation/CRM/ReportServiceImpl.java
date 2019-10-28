package com.procsin.API.Service.Implementation.CRM;

import com.procsin.API.DAO.Pack.OrderDao;
import com.procsin.API.DAO.Pack.OrderLogDao;
import com.procsin.API.Model.ResponseModel.AllInvoicesResponseModel;
import com.procsin.API.Model.ResponseModel.FailStatsResponseModel;
import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.API.Service.Interface.CRM.ReportService;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;
import com.procsin.Network.NetworkClient;
import com.procsin.Network.OrderResponseDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import java.util.List;

import static com.procsin.Static.Queries.GET_INVOICE_DETAILS;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderDao orderRepository;

    @Autowired
    private OrderLogDao orderLogRepository;

    @Autowired
    private EntityManager em;

    @Override
    public List<Orders> getOrders(String fromDate, String toDate) {
        if (fromDate == null || toDate == null) {
            return orderRepository.findLastHundredOrders();
        }
        else {
            return orderRepository.findOrdersByDate(fromDate,toDate);
        }
    }

    @Override
    public List<OrderLog> getOrderLogs(Long orderId) {
        return orderLogRepository.getOrderLogs(orderId);
    }

    @Override
    public Orders findByOrderCode(String orderCode) {
        return null;
    }

    @Override
    public List<Orders> findLikeOrderCode(String orderCode) {
        return orderRepository.findLikeOrderCode(orderCode);
    }

    @Override
    public Orders changeFailedStatus(Boolean didFail, String orderCode) {
        return null;
    }

    @Override
    public FailStatsResponseModel getFailStats(String fromDate, String toDate) {
        return null;
    }

    @Override
    public void setOrderDates() {
        List<Orders> orders = orderRepository.findOrdersWithoutDate();
        for (Orders order : orders) {
            MultiValueMap<String, String > params = new LinkedMultiValueMap<String, String>();
            params.add("OrderCode",order.getOrderCode());
            params.add("OrderStatusId", "2,3,4,5,6,7,8,9,10,11,12,13,1202,1203,1204");
            params.add("Archive","1");
            OrderResponseDataModel tempData = NetworkClient.getInstance().doPost("/order2/getOrders", params, OrderResponseDataModel.class);
            if (tempData != null && tempData.data != null && tempData.data.size() > 0) {
                OrderResponseModel temp = tempData.data.get(0);
                order.setOrderDate(temp.OrderDate);
                orderRepository.save(order);
            }
        }
    }

    @Override
    public void setOrderInvoices() {
        List<Orders> orders = orderRepository.findOrdersWithoutInvoiceRefNumber();
        for (Orders order : orders) {
            try {
                AllInvoicesResponseModel response = (AllInvoicesResponseModel) em.createNativeQuery(GET_INVOICE_DETAILS,AllInvoicesResponseModel.class)
                        .setParameter("InternalDescription",order.getOrderCode()).getSingleResult();
                if(!response.InvoiceNumber.isEmpty()) {
                    order.setInvoiceRefNumber(response.InvoiceNumber);
                }
                if(!response.EInvoiceNumber.isEmpty()) {
                    order.setInvoiceCode(response.EInvoiceNumber);
                }
                orderRepository.save(order);
            }
            catch (Exception e) {
                System.out.println(order.getOrderCode());
            }
        }
    }


}
