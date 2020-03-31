package com.procsin.TrendyolAPI.Model;

import java.util.Date;

public class OrderTransferLog {
    public String orderCode;
    public String fullName;
    public Date orderDate;
    public Date transferDate;
    public String trendyolOrderNumber;
    public String status;

    public OrderTransferLog(String orderCode, String fullName, Date orderDate, Date transferDate, String trendyolOrderNumber, String status) {
        this.orderCode = orderCode;
        this.fullName = fullName;
        this.orderDate = orderDate;
        this.transferDate = transferDate;
        this.trendyolOrderNumber = trendyolOrderNumber;
        this.status = status;
    }
}
