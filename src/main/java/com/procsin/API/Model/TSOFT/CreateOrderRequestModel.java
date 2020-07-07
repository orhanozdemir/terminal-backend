package com.procsin.API.Model.TSOFT;

import com.procsin.Retrofit.Models.TSoft.OrderModel;
import com.procsin.Retrofit.Models.TSoft.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderRequestModel {
    public String OrderCode;

    public String CustomerCode;
//    public String CustomerId;
    public String Currency;
    public int PaymentTypeId;
    public String OrderStatusId;

//    public String InvoiceAddressId;
    public String InvoiceName;
    public String InvoiceMobile;
    public String InvoiceCountry;
    public String InvoiceCity;
    public String InvoiceTown;
    public String InvoiceAddress;

//    public String DeliveryAddressId;
    public String DeliveryName;
    public String DeliveryMobile;
    public String DeliveryCountry;
    public String DeliveryCity;
    public String DeliveryTown;
    public String DeliveryAddress;

    public double OrderTotalPrice;

    public String CargoCode;

    public List<ProductModel> Products;

    public CreateOrderRequestModel(OrderModel orderModel) {
        this.OrderCode = orderModel.OrderCode;
        this.CustomerCode = orderModel.CustomerCode;
//        this.CustomerId = orderModel.CustomerId;
        this.Currency = "TL";
        this.PaymentTypeId = orderModel.PaymentTypeId;
        this.OrderStatusId = orderModel.OrderStatusId;
//        this.InvoiceAddressId = orderModel.InvoiceAddressId;
//        this.DeliveryAddressId = orderModel.DeliveryAddressId;

        this.InvoiceName = orderModel.InvoiceName;
        this.InvoiceMobile = orderModel.InvoiceMobile;
        this.InvoiceCountry = orderModel.InvoiceCountry;
        this.InvoiceCity = orderModel.InvoiceCity;
        this.InvoiceTown = orderModel.InvoiceTown;
        this.InvoiceAddress = orderModel.InvoiceAddress;

        this.DeliveryName = orderModel.DeliveryName;
        this.DeliveryMobile = orderModel.DeliveryMobile;
        this.DeliveryCountry = orderModel.DeliveryCountry;
        this.DeliveryCity = orderModel.DeliveryCity;
        this.DeliveryTown = orderModel.DeliveryTown;
        this.DeliveryAddress = orderModel.DeliveryAddress;

        this.OrderTotalPrice = orderModel.OrderTotalPrice;
        this.CargoCode = orderModel.CargoCode;
        this.Products = new ArrayList<>(orderModel.OrderDetails);
    }

    public String generatePostDataString() {
        String data = "[{";
        data += "\"OrderCode\":" + "\"M" + OrderCode + "\",";
        data += "\"CustomerCode\":" + "\"" + CustomerCode + "\",";
        data += "\"Currency\":" + "\"" + Currency + "\",";
        data += "\"PaymentTypeId\":" + "\"" + PaymentTypeId + "\",";
        data += "\"OrderStatusId\":" + "\"" + 1206 + "\",";

        data += "\"InvoiceName\":" + "\"" + InvoiceName + "\",";
        data += "\"InvoiceMobile\":" + "\"" + InvoiceMobile + "\",";
        data += "\"InvoiceCountry\":" + "\"" + InvoiceCountry + "\",";
        data += "\"InvoiceCity\":" + "\"" + InvoiceCity + "\",";
        data += "\"InvoiceTown\":" + "\"" + InvoiceTown + "\",";
        data += "\"InvoiceAddress\":" + "\"" + InvoiceAddress + "\",";

        data += "\"DeliveryName\":" + "\"" + DeliveryName + "\",";
        data += "\"DeliveryMobile\":" + "\"" + DeliveryMobile + "\",";
        data += "\"DeliveryCountry\":" + "\"" + DeliveryCountry + "\",";
        data += "\"DeliveryCity\":" + "\"" + DeliveryCity + "\",";
        data += "\"DeliveryTown\":" + "\"" + DeliveryTown + "\",";
        data += "\"DeliveryAddress\":" + "\"" + DeliveryAddress + "\",";

//        data += "\"InvoiceAddressId\":" + "\"" + InvoiceAddressId + "\",";
//        data += "\"DeliveryAddressId\":" + "\"" + DeliveryAddressId + "\",";
        data += "\"OrderTotalPrice\":" + "\"" + ((PaymentTypeId == -2) ? 0 : OrderTotalPrice) + "\",";
        data += "\"CargoCode\":" + "\"" + "T7" + "\",";

        data += "\"Products\":[";
        for (ProductModel model : Products) {
            data += "{\"ProductCode\":" + "\"" + model.ProductCode + "\",";
            data += "\"Quantity\":" + "\"" + model.Quantity + "\",";
            data += "\"SellingPrice\":" + "\"" + ((PaymentTypeId == -2) ? 0 : model.DiscountedPrice) + "\"},";
        }

        data = data.substring(0, data.length() - 1);

        data += "]}]";
        return data;
    }
}