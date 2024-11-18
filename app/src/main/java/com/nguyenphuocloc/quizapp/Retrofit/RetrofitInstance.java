package com.nguyenphuocloc.quizapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInstance {

    String baseUrl = "http://192.168.131.1:8080";

    public Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

}
