package com.procsin.DB.Entity.Pack;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FixLog", schema = "sevk", catalog = "PRS_SEVK")
public class FixLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String orderCode;
    private Date date;

    private OrderLog.OrderStatus toStatus;

    @ManyToOne
    private User user;

    public FixLog() {}

    public FixLog(String orderCode, Date date, OrderLog.OrderStatus toStatus, User user) {
        this.orderCode = orderCode;
        this.date = date;
        this.toStatus = toStatus;
        this.user = user;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderLog.OrderStatus getToStatus() {
        return toStatus;
    }

    public void setToStatus(OrderLog.OrderStatus toStatus) {
        this.toStatus = toStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
