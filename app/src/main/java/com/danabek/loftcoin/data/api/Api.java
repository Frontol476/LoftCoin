package com.danabek.loftcoin.data.api;

import com.danabek.loftcoin.data.api.model.RateResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    String CONVERT = "USD";

    @GET("cryptocurrency/listings/latest")
    @Headers("X-CMC_PRO_API_KEY: 0c195eae-cdbb-4bad-8e2c-9526567b6f74")
    Observable<RateResponse> rates(@Query("convert") String convert);
}
