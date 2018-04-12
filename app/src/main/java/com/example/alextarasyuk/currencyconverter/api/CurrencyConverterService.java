package com.example.alextarasyuk.currencyconverter.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CurrencyConverterService {
@GET("latest")
Call<ResponseBody> getCurrency(@Query("base") String from);
}
