package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CreatedOrder", schema = "panel", catalog = "PRS_SEVK")
public class CreatedOrder {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public ReturnedOrder returnedOrder;

    public String newOrderCode;

    public Date createdAt;

    @ManyToOne
    public User createdBy;

    public CreatedOrder() {}
}