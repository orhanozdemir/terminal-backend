package com.procsin.TrendyolAPI.Interface;

import com.procsin.TrendyolAPI.Model.TrendyolOrdersResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrendyolInterface {

    @GET("/sapigw/suppliers/{supplierID}/orders")
    Call<TrendyolOrdersResponseModel> getSingleOrder(@Path("supplierID") long supplierID, @Query("orderNumber") String orderNumber);

}
