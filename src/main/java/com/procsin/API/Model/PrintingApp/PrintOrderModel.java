package com.procsin.API.Model.PrintingApp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PrintOrderModel", schema = "responses", catalog = "PRS_SEVK")
public class PrintOrderModel {

    @Id
    public String id;

    public String orderCode;
    public String fullName;
    public String invoiceURL;

    public PrintOrderModel() {}
}
