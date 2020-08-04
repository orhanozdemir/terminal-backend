package com.procsin.DB.Entity.Pack.Return;

import com.procsin.DB.Entity.UserManagement.User;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ReturnedOrder", schema = "panel", catalog = "PRS_SEVK")
public class ReturnedOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    public PRSOrder order;

    public ReturnedOrderStatus status;
    public String description;
    public String trackingCode;

    public Date createdAt;
    public Date lastUpdatedAt;

    @ManyToOne
    public User createdBy;
    @ManyToOne
    public User lastUpdatedBy;

    public Boolean newOrderCreated;
    public Boolean manuallyCreated;
    public Boolean productsDidReturn;
    public Boolean isCompleted;

    public ReturnedOrder() {}
}
