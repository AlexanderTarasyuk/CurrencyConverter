package com.example.alextarasyuk.currencyconverter.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public abstract class CurrencyConverterServiceBase {

    protected Retrofit mRetrofit;

    public final static OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(2, TimeUnit.MINUTES)
            .build();

    public CurrencyConverterServiceBase(){
        mRetrofit=new Retrofit.Builder()
                .baseUrl("http://api.fixer.io/")
                .client(okHttpClient)
                .build();

    }



}
