package com.example.oleg.newspaper.API;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

        public  static  final String Base_URL = "https://newsapi.org/v2/";
        public static Retrofit retrofit = null;

        public static Retrofit getClient(){
            if (retrofit==null){
                OkHttpClient client = new OkHttpClient.Builder()
                        .addNetworkInterceptor(new StethoInterceptor())
                        .build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(Base_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }

            return retrofit;
        }
    }

