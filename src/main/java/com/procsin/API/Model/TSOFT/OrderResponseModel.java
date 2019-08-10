package com.procsin.API.Model.TSOFT;

import java.util.Date;
import java.util.List;

public class OrderResponseModel {

    public String OrderId;
    public String OrderCode;
    public String DeliveryName;
    public String DeliveryAddress;
    public double OrderTotalPrice;
    public Date OrderDate;
    public List<ProductResponseModel> OrderDetails;

    public int totalProductCount;

    public OrderResponseModel() {}

//    public OrderResponseModel(String orderId, String orderCode, String deliveryName, String deliveryAddress, double orderTotalPrice, String orderDate, List<ProductResponseModel> orderDetails, int totalProductCount) {
//        OrderId = orderId;
//        OrderCode = orderCode;
//        DeliveryName = deliveryName;
//        DeliveryAddress = deliveryAddress;
//        OrderTotalPrice = orderTotalPrice;
//        OrderDate = orderDate;
//        OrderDetails = orderDetails;
//        this.totalProductCount = totalProductCount;
//    }

    public void setTotalProductCount() {
        for (ProductResponseModel product : OrderDetails) {
            if (product.IsPackage.equals("true")) {
                for(ProductResponseModel innerProduct : product.PackageContent) {
                    innerProduct.count = innerProduct.Quantity * innerProduct.PackageQuantity;
                    this.totalProductCount += innerProduct.count;
                }
            }
            else {
                product.count = product.Quantity;
                this.totalProductCount += product.count;
            }
        }
    }

}
