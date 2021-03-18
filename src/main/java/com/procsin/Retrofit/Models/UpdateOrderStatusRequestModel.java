package com.procsin.Retrofit.Models;

import com.procsin.Retrofit.Models.TSoft.StatusEnum;

import java.util.List;

public class UpdateOrderStatusRequestModel {
    public StatusEnum fromStatus;
    public StatusEnum toStatus;
    public List<String> withProducts;
    public List<String> withoutProducts;
}
