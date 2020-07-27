package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrderCall", schema = "panel", catalog = "PRS_SEVK")
public class OrderCall {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public PRSOrder order;

    public String description;

    public Boolean didFail;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public OrderCall() {}
}