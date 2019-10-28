package com.procsin.Retrofit.Interfaces;

import com.procsin.Retrofit.Models.AuthResponseModel;
import com.procsin.Retrofit.Models.InvoiceRequestModel;
import com.procsin.Retrofit.Models.InvoiceResponseModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IISInterface {

    @GET("/IntegratorService/Connect")
    Call<AuthResponseModel> auth();

    @POST("/{token}/OrderService/OrderRToInvoice")
    Call<InvoiceResponseModel> invoice(@Path("token") String token, @Body InvoiceRequestModel invoiceRequestModel);

}
