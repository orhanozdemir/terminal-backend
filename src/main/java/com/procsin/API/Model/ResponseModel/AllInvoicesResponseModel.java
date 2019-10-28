package com.procsin.API.Model.ResponseModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AllInvoicesResponseModel", schema = "responses", catalog = "PRS_SEVK")
public class AllInvoicesResponseModel {

    @Id
    public String id;
    public String InternalDescription;
    public String InvoiceNumber;
    public String EInvoiceNumber;

    public AllInvoicesResponseModel() {
    }
}
