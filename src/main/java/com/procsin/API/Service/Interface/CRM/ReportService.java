package com.procsin.API.Service.Interface.CRM;

import com.procsin.API.Model.ResponseModel.FailStatsResponseModel;
import com.procsin.DB.Entity.Pack.OrderLog;
import com.procsin.DB.Entity.Pack.Orders;

import java.util.List;

public interface ReportService {

    List<Orders> getOrders(String fromDate, String toDate);
    List<OrderLog> getOrderLogs(Long orderId);

    Orders findByOrderCode(String orderCode);
    List<Orders> findLikeOrderCode(String orderCode);
    Orders changeFailedStatus(Boolean didFail, String orderCode);
    FailStatsResponseModel getFailStats(String fromDate, String toDate);

    void setOrderDates();
}