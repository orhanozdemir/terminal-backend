package com.procsin.DB.Entity.Pack;

import com.procsin.API.Model.TSOFT.OrderResponseModel;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders", schema = "sevk", catalog = "PRS_SEVK")
public class Orders {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String orderCode;
    private String deliveryName;
    private double totalCost;
    private int totalProductCount;
    private Date orderDate;

    private Boolean didFail;

    private String invoiceCode;
    private String invoiceRefNumber;

    public Orders() {}

    public Orders(String orderCode, String deliveryName, double totalCost, int totalProductCount, Date orderDate, Boolean didFail, String invoiceCode, String invoiceRefNumber) {
        this.orderCode = orderCode;
        this.deliveryName = deliveryName;
        this.totalCost = totalCost;
        this.totalProductCount = totalProductCount;
        this.orderDate = orderDate;
        this.didFail = didFail;
        this.invoiceCode = invoiceCode;
        this.invoiceRefNumber = invoiceRefNumber;
    }

    public Orders(OrderResponseModel model) {
        this.orderCode = model.OrderCode;
        this.deliveryName = model.DeliveryName;
        this.totalCost = model.OrderTotalPrice;
        this.totalProductCount = model.totalProductCount;
    }

    public Orders(OrderModel model) {
        this.orderCode = model.OrderCode;
        this.deliveryName = model.DeliveryName;
        this.totalCost = model.OrderTotalPrice;
        this.orderDate = model.OrderDate;
        this.totalProductCount = model.totalProductCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalProductCount() {
        return totalProductCount;
    }

    public void setTotalProductCount(int totalProductCount) {
        this.totalProductCount = totalProductCount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean getDidFail() {
        return didFail;
    }

    public void setDidFail(Boolean didFail) {
        this.didFail = didFail;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceRefNumber() {
        return invoiceRefNumber;
    }

    public void setInvoiceRefNumber(String invoiceRefNumber) {
        this.invoiceRefNumber = invoiceRefNumber;
    }
}
