package com.procsin.API.Model;

import com.procsin.DB.Entity.Pack.Campaign;
import com.procsin.Retrofit.Models.TSoft.OrderModel;

public class OrderLogSuccessModel {

    public Boolean success;
    public String message;
    public Campaign campaign;
    public OrderModel order;

    public OrderLogSuccessModel() {}

    public OrderLogSuccessModel(Boolean success, String message, Campaign campaign, OrderModel order) {
        this.success = success;
        this.message = message;
        this.campaign = campaign;
        this.order = order;
    }
}
