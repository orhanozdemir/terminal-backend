package com.procsin.API.Service.Interface;

import com.procsin.API.Model.ItemBarcodeResponseModel;

import java.util.List;

public interface ProductService {

    List<ItemBarcodeResponseModel> getItemBarcodeList();

}
