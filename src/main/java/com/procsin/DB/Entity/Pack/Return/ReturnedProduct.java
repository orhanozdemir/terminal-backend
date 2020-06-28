package com.procsin.DB.Entity.Pack.Return;

import javax.persistence.*;

@Entity
@Table(name = "ReturnedProduct", schema = "sevk", catalog = "PRS_SEVK")
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

    public ReturnedProduct() {}
}
