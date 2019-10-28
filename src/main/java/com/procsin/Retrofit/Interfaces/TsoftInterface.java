package com.procsin.Retrofit.Interfaces;

import com.procsin.API.Model.GenericResponse;
import com.procsin.API.Model.TSOFT.GenericTsoftResponseModel;
import com.procsin.Retrofit.Models.TSoft.OrderDataModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TsoftInterface {

    @FormUrlEncoded
    @POST("order2/getOrders")
    Call<OrderDataModel> getOrders(@Field("token") String token, @Field("OrderStatusId") String OrderStatusId, @Field("FetchPackageContent") Boolean FetchPackageContent,
                                   @Field("limit") int limit, @Field("orderby") String orderBy, @Field("f") String f);

    @FormUrlEncoded
    @POST("order2/updateOrderStatusAs/1202")
    Call<GenericTsoftResponseModel> updateOrderStatusToPack(@Field("token") String token, @Field("data") String data);

    @FormUrlEncoded
    @POST("order2/updateOrderStatusAs/1203")
    Call<GenericTsoftResponseModel> updateOrderStatusToSupplement(@Field("token") String token, @Field("data") String data);

    @FormUrlEncoded
    @POST("order2/updateOrderStatusAs/5")
    Call<GenericTsoftResponseModel> updateOrderStatusToReady(@Field("token") String token, @Field("data") String data);

    @FormUrlEncoded
    @POST("order2/updateOrderStatusAs/1204")
    Call<GenericTsoftResponseModel> updateOrderStatusToPreparing(@Field("token") String token, @Field("data") String data);

}
