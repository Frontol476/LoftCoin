package com.danabek.loftcoin.data.api;

import com.danabek.loftcoin.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInitializer {

    private static final String BASE_URL = "https://pro-api.coinmarketcap.com/v1/";

    public Api init() {
        Gson gson = createGson();
        OkHttpClient client = createClient();
        Retrofit retrofit = createRetrofit(gson, client);
        return createApi(retrofit);
    }

    private Api createApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }

    private Gson createGson() {
        return new GsonBuilder()
                .create();
    }

    private OkHttpClient createClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BASIC : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    private Retrofit createRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
