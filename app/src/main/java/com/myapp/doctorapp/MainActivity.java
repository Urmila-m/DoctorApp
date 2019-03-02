package com.myapp.doctorapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("gender", "id", "name", "email", "user_birthday"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Log.e("FbLOGIN", "onSuccess: "+loginResult.getAccessToken().getUserId()+" "+loginResult.getAccessToken().getToken() );
                final String[] id = new String[1];
                final String[] first_name = new String[1];
                final String[] last_name = new String[1];
                final String[] gender = new String[1];
                final String[] birthday = new String[1];
                final String[] email=new String[1];

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("FBLOGIN", "fb json object: " + object);
                                    Log.e("FBLOGIN", "fb graph response: " + response);

                                    id[0] = object.getString("id");
                                    first_name[0] = object.getString("first_name");
                                    last_name[0] = object.getString("last_name");
                                    gender[0] = object.getString("user_gender");
                                    birthday[0] = object.getString("user_birthday");
                                    email[0]=object.getString("email");

//                                    String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
//
//                                    String email;
//                                    if (object.has("email")) {
//                                        email = object.getString("email");
//                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Log.e("FBLOGIN", "onSuccess: "+" "+id[0]+" "+first_name[0]+" "+last_name[0]+" "+birthday[0]+" "+gender[0]+" "+email[0]);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "first_name, last_name, user_gender, user_birthday, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
