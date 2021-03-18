package com.procsin.Retrofit.Interfaces;

import com.procsin.Retrofit.Models.MNG.RequestEnvelope;
import com.procsin.Retrofit.Models.MNG.Response.ResponseEnvelope;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MNGInterface {
    @Headers({"Content-Type: text/xml"})
    @POST("/musterikargosiparis/musterikargosiparis.asmx")
    Call<ResponseEnvelope> getGonderiBarkod(@Query("op") String op, @Body RequestEnvelope requestEnvelope);
}
