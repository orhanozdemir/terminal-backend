package com.procsin.Retrofit.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderDetailsModel", schema = "responses", catalog = "PRS_SEVK")
public class OrderDetailsModel {

    public String orderNumberRetailSale;
    public String orderDate;
    public String RetailCustomerCode;
    public String TSNUMBER;
    public String UrunKodu;
    public double price;
    @Id
    public String orderline;
    public int Qty1;
    public String odeme;
    public double odemetutar;
    public String faturaadresi;
    public String OrderHeaderID;
    public String DocCurrencyCode;
    public int PaymentTypeCode;
    public int InstallmentCount;

    public OrderDetailsModel() {}
}
