package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedOrderLog", schema = "panel", catalog = "PRS_SEVK")
public class ReturnedOrderLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public ReturnedOrder returnedOrder;

    @ManyToOne
    public User user;

    public ReturnedOrderStatus status;

    public String updateNote;

    public Date createdAt;

    public ReturnedOrderLog() {}

    public ReturnedOrderLog(ReturnedOrder returnedOrder, User user, ReturnedOrderStatus status, String description) {
        this.returnedOrder = returnedOrder;
        this.user = user;
        this.status = status;
        this.updateNote = returnedOrder.order.orderCode + " - " + user.getUsername() + " durumu (" + status + ") olarak güncelledi. Açıklama : " + description;
        this.createdAt = new Date();
    }
}
