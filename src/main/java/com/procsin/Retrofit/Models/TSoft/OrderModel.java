package com.procsin.Retrofit.Models.TSoft;

import com.google.gson.annotations.Expose;
import com.procsin.Retrofit.Models.OrderDetailsModel;
import com.procsin.TrendyolAPI.Model.TrendyolOrder;
import com.procsin.TrendyolAPI.Model.TrendyolProductLine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderModel {
    public String OrderCode;
    public String DeliveryName;
    public String DeliveryAddress;
    public String Cargo;
    public String OrderStatusId;
    public Date OrderDate;
    public long OrderDateTimeStamp;
    public double OrderTotalPrice;
    public String PaymentType;

    public String Application;

    public List<ProductModel> OrderDetails;

    @Expose
    public double totalCost;
    @Expose
    public int totalProductCount;

    public OrderModel() {}

    public OrderModel(TrendyolOrder model) {
        this.OrderCode = "TY"  + model.orderNumber;
        this.DeliveryName = model.shipmentAddress.fullName;
        this.DeliveryAddress = model.shipmentAddress.fullAddress;
        this.Cargo = "MNG";
        this.OrderDate = new Date(model.orderDate);
        this.OrderDateTimeStamp = model.orderDate;
        this.OrderTotalPrice = Double.valueOf(model.totalPrice);
        this.Application = "Trendyol";

        List<ProductModel> products = new ArrayList<>();
        for (TrendyolProductLine line : model.lines) {
            ProductModel product = new ProductModel();
            product.Quantity = line.quantity;
            product.ProductCode = line.merchantSku;
            product.ProductName = line.productName;
            product.PackageContent = null;
            product.Barcode = line.barcode;
            product.IsPackage = "0";
            products.add(product);
        }
        this.OrderDetails = products;

        setTotalProductCount();
    }

    public void setTotalProductCount() {
        int tempTotal = 0;
        for (ProductModel model : OrderDetails) {
            if (model.IsPackage.equals("1")) {
                for (ProductModel innerModel : model.PackageContent) {
                    innerModel.Quantity = innerModel.Quantity - innerModel.RefundCount;
                    if (innerModel.Quantity == 0) {
                        OrderDetails.remove(innerModel);
                    }
                    if (innerModel.count == null) {
                        innerModel.count = model.Quantity;
                        innerModel.setTotalCount(innerModel.count);
                        tempTotal += innerModel.count;
                    }
                }
            }
            else {
                if (model.count == null) {
                    model.Quantity = model.Quantity - model.RefundCount;
                    if (model.Quantity == 0) {
                        OrderDetails.remove(model);
                    }
                    model.count = model.Quantity;
                    model.setTotalCount(model.count);
                    tempTotal += model.count;
                }
            }
        }
        totalProductCount = tempTotal;
    }
}
