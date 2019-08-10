package com.procsin.DB.Entity.Pack.report;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class OrderReportResponseModel {

    @Id
    public String id;
    public String deliveryName;
    public String orderCode;
    public double totalCost;
    public int totalProductCount;
    public String username;
    public Date date;

    public OrderReportResponseModel() {}
}
