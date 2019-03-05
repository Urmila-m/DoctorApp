package com.myapp.doctorapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnDataRetrievedListener {

    private static final String API="ApiBackgroundTask";
    private static final String SIGNUP="SignUpWithFacebookTask";

    SignUpWithFacebookTask task;
//    Button btn_custom_fb_sign_in;
    Button btnSignUp;
    ApiBackgroundTask apiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        btn_custom_fb_sign_in=findViewById(R.id.custom_btn_fb_sign_in);
//        btn_custom_fb_sign_in.setOnClickListener(this);

        setContentView(R.layout.sign_up_options_layout);

        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        boolean isLogin=accessToken!=null&&!accessToken.isExpired();

        if (isLogin){
            startActivity(new Intent(this, AfterLoginActivity.class));
        }

        else {

            btnSignUp = findViewById(R.id.btn_sign_up_options_fb);
            btnSignUp.setOnClickListener(this);

            apiTask = new ApiBackgroundTask();
            task = new SignUpWithFacebookTask(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            task.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        task.registerFbCallback();


    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        if (source.equals(API)) {
            Log.e("TAG", "onDataRetrieved: Register successful: "+bundle.getString("errorMsg")+"!!!" );
        }
        else if (source.equals(SIGNUP)) {
            Log.e("Tag", "onDataRetrieved: " + bundle.getString("image"));
            apiTask.insertResponse(bundle, this);
            Intent intent=new Intent(MainActivity.this, AfterLoginActivity.class);
            intent.putExtra("data", bundle);
            startActivity(intent);
        }

    }
}
