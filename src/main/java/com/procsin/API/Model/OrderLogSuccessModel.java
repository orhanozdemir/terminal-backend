package com.procsin.API.Model;

import com.procsin.DB.Entity.Pack.Campaign;

public class OrderLogSuccessModel {

    public Boolean success;
    public String message;
    public Campaign campaign;

    public OrderLogSuccessModel() {}

    public OrderLogSuccessModel(Boolean success, String message, Campaign campaign) {
        this.success = success;
        this.message = message;
        this.campaign = campaign;
    }
}
