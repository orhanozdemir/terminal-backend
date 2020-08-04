package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedProduct", schema = "panel", catalog = "PRS_SEVK")
public class ReturnedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public ReturnedOrder returnedOrder;

    public String productCode;
    public String productName;

    public int stableQuantity;
    public int damagedQuantity;
    public int totalQuantity;

    public Date createdAt;
    @ManyToOne
    public User createdBy;

    public ReturnedProduct() {}
}
