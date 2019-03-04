package com.myapp.doctorapp.interfaces;

import android.os.Bundle;

import com.myapp.doctorapp.model.PostResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("doctorAppAPI.php")
    @FormUrlEncoded
    Call<PostResponse
            > insertData(@Field("action") String insert,
                                  @Field("name") String name,
                                  @Field("mobile") String mobile,
                                  @Field("id") String id,
                                  @Field("dob") String dob,
                                  @Field("gender") String gender,
                                  @Field("height") String height,
                                  @Field("weight") String weight,
                                  @Field("password") String password,
                                  @Field("image") String image,
                                  @Field("email") String email);

//    @POST("doctorAppAPI.php")
//    @FormUrlEncoded
//    Call<PostResponse> insertData(@Field("action") String insert,
//                                  @Field("user_data") Bundle bundle);

}
