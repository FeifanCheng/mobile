package com.mobile.factory.helper.network;

import com.mobile.util.Config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkHelper {

    public static Retrofit getRetrofit(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        // 设置请求地址并返回
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder.baseUrl(Config.NETWORK_URL).client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).build();
    }
}
