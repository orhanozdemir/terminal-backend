package com.procsin.DB.Entity.CRM;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedProduct", schema = "crm", catalog = "PRS_SEVK")
public class ReturnedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    public String productCode;

    public int quantity;

    public String orderCode;

    public String reason;

    public Date date;

    public ReturnedProduct() {}
}
