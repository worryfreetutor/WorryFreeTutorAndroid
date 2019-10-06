package com.example.kaixuan.worryfreetutor.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GetRetrofit {
    public static Retrofit  getR(){
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BaseURL.getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return  retrofit;
    }
}
