package com.procsin.TrendyolAPI.Model;

import java.util.List;

public class TrendyolOrder {
    //Customer
    public TrendyolAddress shipmentAddress;
    public String orderNumber;
    public TrendyolAddress invoiceAddress;
    public String customerFirstName;
    public String customerLastName;
    public String customerId;
    public String customerEmail;
    public String cargoTrackingNumber;
    public String tcIdentityNumber;

    //Order
    public long orderDate;
    public String shipmentPackageStatus;

    //Product
    public List<TrendyolProductLine> lines;

    //Invoice
    public double grossAmount;
    public double totalDiscount;
    public String currencyCode;
    public String totalPrice;
}
