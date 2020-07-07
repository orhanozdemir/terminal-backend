package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedOrderLog", schema = "sevk", catalog = "PRS_SEVK")
public class ReturnedOrderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public ReturnedOrder order;

    @ManyToOne
    public User user;

    public ReturnedOrder.ReturnedOrderStatus status;

    public Date createdAt;

    public ReturnedOrderLog() {}

    public ReturnedOrderLog(ReturnedOrder order, ReturnedOrder.ReturnedOrderStatus status, User user) {
        this.order = order;
        this.user = user;
        this.status = status;
        this.createdAt = new Date();
    }
}
