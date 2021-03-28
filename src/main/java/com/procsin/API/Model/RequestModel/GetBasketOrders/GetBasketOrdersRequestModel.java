package com.procsin.API.Model.RequestModel.GetBasketOrders;

import com.procsin.Retrofit.Models.TSoft.StatusEnum;

import java.util.List;

public class GetBasketOrdersRequestModel {
    public StatusEnum fromStatus;
    public List<GetBasketOrdersProductModel> products;
}
