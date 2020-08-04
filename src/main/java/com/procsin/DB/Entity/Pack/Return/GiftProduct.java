package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "GiftProduct", schema = "panel", catalog = "PRS_SEVK")
public class GiftProduct {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    public Gift gift;

    public String productCode;
    public String productName;
    public int quantity;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public GiftProduct() {}
}
