package com.myapp.doctorapp.backgroundtasks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import static com.myapp.doctorapp.Globals.SIGNUP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpWithFacebookTask {

    private static final String EMAIL = "email";
    private static final String PROFILE="public_profile";
    private static final String GENDER="user_gender";
    private static final String BIRTHDAY="user_birthday";

    private CallbackManager callbackManager;
    private AppCompatActivity activity;
    private FacebookCallback<LoginResult> fbCallback;
    private LoginManager loginManager;
    private OnDataRetrievedListener listener;

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public SignUpWithFacebookTask(AppCompatActivity activity){
        Log.e("TAG", "SignUpWithFacebookTask: constructor called");
        this.activity=activity;
        loginManager=LoginManager.getInstance();
        callbackManager=CallbackManager.Factory.create();
        fbCallback=new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("TAG", "onSuccess: register callback called, on Success initial step");
                GraphRequest request=initializeGraphRequest(loginResult);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, gender, birthday, email");
                request.setParameters(parameters);
                request.executeAsync();

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

    public void registerFbCallback(){
        Log.e("TAG", "registerFbCallback: CALLED");
        loginManager.logInWithReadPermissions(activity, Arrays.asList(EMAIL, PROFILE, BIRTHDAY, GENDER));
    }

    private GraphRequest initializeGraphRequest(LoginResult loginResult){
        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if (activity instanceof OnDataRetrievedListener) {
                                listener = (OnDataRetrievedListener) activity;

                                Log.e("FBLOGIN", "fb json object: " + object);
                                Log.e("FBLOGIN", "fb graph response: " + response);

                                String id = object.getString("id");
                                String first_name = object.getString("first_name");
                                String last_name = object.getString("last_name");
                                String gender = object.getString("gender");
                                String birthday = object.getString("birthday");
                                String email = object.getString("email");
                                String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";

                                Bundle bundle = new Bundle();
                                bundle.putString("id", id);
                                bundle.putString("name", first_name +" "+last_name);
                                bundle.putString("gender", gender);
                                bundle.putString("birthday", birthday);
                                bundle.putString("email", email);
                                bundle.putString("image", image_url);
                                listener.onDataRetrieved(SIGNUP, bundle);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("TAG", "onCompleted: JSON exception"+e.getMessage());
                        }
                    }
                });

        return request;
    }

}
