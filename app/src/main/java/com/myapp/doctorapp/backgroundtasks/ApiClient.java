package com.myapp.doctorapp.backgroundtasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL="http://192.168.1.72/";
    static Retrofit retrofitObj;

    public static Retrofit getRetrofitObj(){
        OkHttpClient client=new OkHttpClient();

        Gson gson=new GsonBuilder()
                .setLenient()
                .create();

        if(retrofitObj==null){
            retrofitObj=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitObj;
    }
}
