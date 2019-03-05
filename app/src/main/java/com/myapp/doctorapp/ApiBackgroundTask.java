package com.myapp.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.interfaces.ApiInterface;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.PostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.doctorapp.Globals.anInt;

public class ApiBackgroundTask {
    private static final String CLASS_NAME="ApiBackgroundTask";
    private ApiInterface apiInterface;

    ApiBackgroundTask(){
        anInt=1;
        apiInterface=ApiClient.getRetrofitObj().create(ApiInterface.class);
    }

    public void insertResponse(final Bundle bundle, final OnDataRetrievedListener listener) {
        if (listener != null) {
            String height=(bundle.getString("height")==null)?"40":bundle.getString("height");
            String weight=(bundle.getString("weight")==null)?"40":bundle.getString("weight");
            String password=(bundle.getString("password")==null)?"":bundle.getString("password");
            String mobile=(bundle.getString("mobile")==null)?"":bundle.getString("mobile");
            String id=(bundle.getString("id")==null)?(anInt++)+"":bundle.getString("id");
            String image=(bundle.getString("image")==null)?"R.drawable.default_image":bundle.getString("image");

            apiInterface.insertData("insertData",
                    bundle.getString("name"),
                    mobile,
                    id,
                    bundle.getString("birthday"),
                    bundle.getString("gender"),
                    height,
                    weight,
                    password,
                    image,
                    bundle.getString("email"))
            .enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    Bundle b=new Bundle();
                    b.putString("errorMsg", response.body().getErrorMsg());
                    b.putBoolean("success", response.body().isSuccess());
                    listener.onDataRetrieved(CLASS_NAME, b);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
//                    TODO toast error message and check throwable
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });

        }
    }
}
