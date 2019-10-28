package com.procsin.API.Service.Interface.Pack;

import com.procsin.Retrofit.Models.InvoiceResponseModel;

public interface IISService {
    InvoiceResponseModel createInvoice(String orderCode);
}
