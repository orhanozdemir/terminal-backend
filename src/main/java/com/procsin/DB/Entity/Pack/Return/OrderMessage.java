package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OrderMessage", schema = "panel", catalog = "PRS_SEVK")
public class OrderMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public PRSOrder order;

    public String message;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public OrderMessage() {}
}
