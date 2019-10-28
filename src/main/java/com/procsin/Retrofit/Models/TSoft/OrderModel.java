package com.procsin.Retrofit.Models.TSoft;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;

public class OrderModel {
    public String OrderCode;
    public String DeliveryName;
    public String DeliveryAddress;
    public Date OrderDate;
    public double OrderTotalPrice;
    public List<ProductModel> OrderDetails;

    @Expose
    public double totalCost;
    @Expose
    public int totalProductCount;

    public void setTotalProductCount() {
        int tempTotal = 0;
        for (ProductModel model : OrderDetails) {
            if (model.IsPackage.equals("1")) {
                for (ProductModel innerModel : model.PackageContent) {
                    if (innerModel.count == null) {
                        innerModel.count = model.Quantity;
                        innerModel.setTotalCount(innerModel.count);
                        tempTotal += innerModel.count;
                    }
                }
            }
            else {
                if (model.count == null) {
                    model.count = model.Quantity;
                    model.setTotalCount(model.count);
                    tempTotal += model.count;
                }
            }
        }
        totalProductCount = tempTotal;
    }
}
