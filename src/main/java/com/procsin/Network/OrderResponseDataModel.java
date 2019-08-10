package com.procsin.Network;

import java.util.List;

public class OrderResponseDataModel {

    public String success;
    public List<com.procsin.API.Model.TSOFT.OrderResponseModel> data;

    public OrderResponseDataModel() {}

    public OrderResponseDataModel(String success, List<com.procsin.API.Model.TSOFT.OrderResponseModel> data) {
        this.success = success;
        this.data = data;
    }
}