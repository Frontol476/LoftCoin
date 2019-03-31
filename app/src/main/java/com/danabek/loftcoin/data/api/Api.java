package com.danabek.loftcoin.data.api;

import com.danabek.loftcoin.data.api.model.RateResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    String CONVERT = "USD,EUR,RUB";

    @GET("cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: a1082949-25ab-4024-875c-f481f9b81c61")
    Call<RateResponse> rates(@Query("convert") String convert);
}
