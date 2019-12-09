package com.procsin.API.Model.ResponseModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RetailCustomerModel", schema = "responses", catalog = "PRS_SEVK")
public class RetailCustomerModel {

    @Id
    public String id;

    public String FirstName;
    public String LastName;
    public String IdentityNum;
    public String CommAddress;

    public RetailCustomerModel() {}
}
