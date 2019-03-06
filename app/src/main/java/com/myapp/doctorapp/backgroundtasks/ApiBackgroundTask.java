package com.myapp.doctorapp.backgroundtasks;

import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.interfaces.ApiInterface;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.model.User;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.myapp.doctorapp.Globals.API_GET_EMAIL;
import static com.myapp.doctorapp.Globals.API_GET_USER;
import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.anInt;

public class ApiBackgroundTask {
    private ApiInterface apiInterface;

    public ApiBackgroundTask(){
        anInt=1;
        apiInterface= ApiClient.getRetrofitObj().create(ApiInterface.class);
    }

    public void insertResponse(final Bundle bundle, final OnDataRetrievedListener listener) {
        if (listener != null) {
            String height=(bundle.getString("height")==null)?"0":bundle.getString("height");
            String weight=(bundle.getString("weight")==null)?"0":bundle.getString("weight");
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
                    listener.onDataRetrieved(API_INSERT, b);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
//                    TODO toast error message and check throwable
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });

        }
    }

    public void getEmailPassword(final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getEmailPasswordList("getEmailList").enqueue(new Callback<List<EmailPasswordResponse>>() {
                @Override
                public void onResponse(Call<List<EmailPasswordResponse>> call, Response<List<EmailPasswordResponse>> response) {
                    List<EmailPasswordResponse> list=response.body();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("email_password_list", (Serializable) list);
                    listener.onDataRetrieved(API_GET_EMAIL, bundle);
                }

                @Override
                public void onFailure(Call<List<EmailPasswordResponse>> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

    public void getUser(String email, final OnDataRetrievedListener listener){
        if (listener!=null){
            apiInterface.getUser("getRecordWithEmail", email).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user=response.body();
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("User",user);
                    Log.e("TAG", "onResponse: "+user.getDOB());
                    listener.onDataRetrieved(API_GET_USER, bundle);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }
}
