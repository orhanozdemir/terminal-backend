package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PRSOrder", schema = "panel", catalog = "PRS_SEVK")
public class PRSOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(unique = true, nullable =  false)
    public String orderCode;
    public String paymentType;
    public double amount;

    public String customerCode;
    public String customerFullName;
    public String deliveryAddress;
    public String email;
    public String phoneNumber;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public PRSOrder() {}

    public PRSOrder(OrderModel orderModel, User user) {
        this.orderCode = orderModel.OrderCode;
        this.paymentType = orderModel.PaymentType;
        this.amount = orderModel.OrderTotalPrice;
        this.customerCode = orderModel.CustomerCode;
        this.customerFullName = orderModel.DeliveryName;
        this.deliveryAddress = orderModel.DeliveryAddress;
        this.email = orderModel.CustomerUsername;
        this.phoneNumber = orderModel.DeliveryMobile;
        this.createdAt = new Date();
        this.createdBy = user;
    }
}
