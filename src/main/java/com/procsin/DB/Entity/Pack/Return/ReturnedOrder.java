package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedOrder", schema = "sevk", catalog = "PRS_SEVK")
public class ReturnedOrder {

    public enum ReturnedOrderStatus {
        MUSTERI_HIZMETLERI_BEKLENIYOR,
        YENIDEN_CIKIS_BEKLENIYOR,
        YENIDEN_CIKIS_SAGLANDI,
        KISMI_GONDERIM_BEKLENIYOR,
        KISMI_GONDERIM_SAGLANDI,
        TESLIM_ALINMAYAN_SIPARIS,
        GIDER_PUSULASI_KESILDI,
        MANUEL_OLUSTURULDU
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String orderCode;
    public String customerFullName;
    public ReturnedOrderStatus status;
    public Double orderAmount;
    public String orderType;
    public String description;
    public Date createdAt;
    public Date lastUpdatedAt;

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User lastUpdatedBy;

    public String trackingCode;

    public String newOrderCode;

    public Boolean newOrderCreated;
    public Boolean isCompleted;

    public ReturnedOrder() {}

    public ReturnedOrder(OrderModel orderModel, User user) {
        this.orderCode = orderModel.OrderCode;
        this.customerFullName = orderModel.DeliveryName;
        this.status = ReturnedOrderStatus.MUSTERI_HIZMETLERI_BEKLENIYOR;
        this.orderAmount = orderModel.OrderTotalPrice;
        this.createdAt = new Date();
        this.createdBy = user;
        this.lastUpdatedAt = new Date();
        this.lastUpdatedBy = user;
        this.description = "";
        this.trackingCode = "";
    }
}
