package com.procsin.API.Model.TSOFT;

import java.util.List;

public class OrderResponseModel {

    public String OrderId;
    public String OrderCode;
    public String DeliveryName;
    public String DeliveryAddress;
    public double OrderTotalPrice;
    public List<ProductResponseModel> OrderDetails;

    public int totalProductCount;

    public OrderResponseModel() {}

    public OrderResponseModel(String orderId, String orderCode, String deliveryName, String deliveryAddress, double orderTotalPrice, List<ProductResponseModel> orderDetails) {
        OrderId = orderId;
        OrderCode = orderCode;
        DeliveryName = deliveryName;
        DeliveryAddress = deliveryAddress;
        OrderTotalPrice = orderTotalPrice;
        OrderDetails = orderDetails;
    }

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
