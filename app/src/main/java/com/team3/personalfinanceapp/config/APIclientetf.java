package com.team3.personalfinanceapp.config;

import com.google.gson.Gson;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIclientetf {
    private Retrofit retrofit;

    public APIclientetf() {
        initializeRetrofit();
    }


    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twelvedata.com/")
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


}



