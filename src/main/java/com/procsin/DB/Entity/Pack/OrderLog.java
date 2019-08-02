package com.procsin.DB.Entity.Pack;

import com.procsin.DB.Entity.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrderLog", schema = "sevk", catalog = "PRS_SEVK")
public class OrderLog {

    public enum OrderStatus {
        URUN_PAKETLENIYOR,KARGO_HAZIR,TEDARIK_SURECINDE,URUN_PAKETLENIYOR_IPTAL, URUN_HAZIRLANIYOR
    }

//    public static int getOrderStatusID(OrderStatus status) {
//        switch (status) {
//            case URUN_PAKETLENIYOR:
//                return 1202;
//            case TEDARIK_SURECINDE:
//                return 1203;
//            case URUN_PAKETLENIYOR_IPTAL:
//                return 1204;
//            case URUN_HAZIRLANIYOR:
//                return 1204;
//            case KARGO_HAZIR:
//                return 5;
//        }
//        return -1;
//    }

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Orders order;

    private OrderStatus status;

    @ManyToOne
    private User user;

    private Date date;

    public OrderLog() {}

    public OrderLog(Orders order, OrderStatus status, User user) {
        this.order = order;
        this.status = status;
        this.user = user;
        this.date = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
