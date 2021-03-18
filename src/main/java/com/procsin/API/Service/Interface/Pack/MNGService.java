package com.procsin.API.Service.Interface.Pack;

import com.procsin.API.Model.ResponseModel.MNGBarcodeResponseModel;

public interface MNGService {
    MNGBarcodeResponseModel getMNGBarcode(String orderCode);
}
