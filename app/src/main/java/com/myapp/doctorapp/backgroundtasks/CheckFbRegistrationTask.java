package com.myapp.doctorapp.backgroundtasks;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;

import java.util.Arrays;

import static com.myapp.doctorapp.Globals.CHECK_REGISTRATION;

public class CheckFbRegistrationTask {

    private static final String PROFILE="public_profile";

    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> fbCallback;
    private LoginManager loginManager;
    private OnDataRetrievedListener listener;
    private Context context;

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public CheckFbRegistrationTask(Context context) {
        this.context=context;
        if (context instanceof OnDataRetrievedListener){
            listener= (OnDataRetrievedListener) context;
        }
        if (listener!=null){
            loginManager=LoginManager.getInstance();
            callbackManager=CallbackManager.Factory.create();
            fbCallback=new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    String id=loginResult.getAccessToken().getUserId();
                    Log.e("TAG", "onSuccess: "+id);
                    Bundle bundle=new Bundle();
                    bundle.putString("id", id);
                    listener.onDataRetrieved(CHECK_REGISTRATION, bundle);
                }

                @Override
                public void onCancel() {
                    Log.e("TAG", "onCancel: called!!!...");

                }

                @Override
                public void onError(FacebookException error) {
                    Log.e("TAG", "onError: called!!!..");
                    Log.e("TAg", "onError: "+error.getMessage());
                }
            };

            loginManager.registerCallback(callbackManager, fbCallback);
        }
    }

    public void registerFbCallback(){
        Log.e("TAG", "registerFbCallback: CALLED");
        loginManager.logInWithReadPermissions((Activity) context, Arrays.asList(PROFILE));
    }
}
