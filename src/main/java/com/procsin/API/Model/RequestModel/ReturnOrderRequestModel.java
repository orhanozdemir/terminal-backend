package com.procsin.API.Model.RequestModel;

import com.procsin.DB.Entity.Pack.Return.ReturnedProduct;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

import java.util.List;

public class ReturnOrderRequestModel {

    public OrderModel orderModel;
    public List<ReturnedProduct> products;

}
