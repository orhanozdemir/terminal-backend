package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GiftCustomer", schema = "panel", catalog = "PRS_SEVK")
public class GiftCustomer {

    @Id
    @GeneratedValue
    public Long id;

    public String name;
    public String address;
    public String phoneNumber;
    public String email;
    public Boolean didComplete;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public GiftCustomer() {}
}
