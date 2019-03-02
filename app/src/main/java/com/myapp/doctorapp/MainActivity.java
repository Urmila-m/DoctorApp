package com.myapp.doctorapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EMAIL = "email";
    private static final String PROFILE="public_profile";
    private static final String GENDER="user_gender";
    private static final String BIRTHDAY="user_birthday";
    private static final int RC_SIGN_IN = 1;
    TextView textView;

    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener listener;

    CallbackManager callbackManager;

    Button btn_custom_fb_sign_in;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_custom_fb_sign_in=findViewById(R.id.custom_btn_fb_sign_in);
        btn_custom_fb_sign_in.setOnClickListener(this);

        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.e("FbLOGIN", "onSuccess: " + loginResult.getAccessToken().getUserId() + " " + loginResult.getAccessToken().getToken());
                        final String[] id = new String[1];
                        final String[] first_name = new String[1];
                        final String[] last_name = new String[1];
                        final String[] gender = new String[1];
                        final String[] birthday = new String[1];
                        final String[] email = new String[1];

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
                                            gender[0] = object.getString("gender");
                                            birthday[0] = object.getString("birthday");
                                            email[0] = object.getString("email");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Log.e("FBLOGIN", "onSuccess: " + " " + id[0] + " " + first_name[0] + " " + last_name[0] + " " + birthday[0] + " " + gender[0] + " " + email[0]);
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "first_name, last_name, gender, birthday, email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(MainActivity.this, "Canceled!!! onCancel() called", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onCancel: called!!!!/... ");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(MainActivity.this, "onError() called", Toast.LENGTH_SHORT).show();
                        Log.e("TAG", "onError: called....!!!");
                    }
                });

        FirebaseApp.initializeApp(this);

        firebaseAuth=FirebaseAuth.getInstance();
        SignInButton signInButton=findViewById(R.id.btn_google_sign_in);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
//                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(this);


        final LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE, GENDER, BIRTHDAY));

        textView=findViewById(R.id.tv_google_status);

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
                                    gender[0] = object.getString("gender");
                                    birthday[0] = object.getString("birthday");
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
                parameters.putString("fields", "first_name, last_name, gender, birthday, email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Canceled!!! onCancel() called", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onCancel: called!!!!/... ");
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this, "onError() called", Toast.LENGTH_SHORT).show();
                Log.e("TAG", "onError: called....!!!");
            }
        });

        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    textView.setText("Logged in already");
                }
                else{
                    textView.setText("Not logged in yet");
                }
            }
        };
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String email=account.getEmail();
            String id=account.getId();
//            String tokenId=account.getIdToken();
            String name=account.getDisplayName();
            Toast.makeText(this, "SIGNED in successfully!!!", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "handleSignInResult: "+name+" "+email+" "+id+" ");

        } catch (ApiException e) {
            Log.e("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
//            if (requestCode==RESULT_OK)
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signIn(GoogleSignInClient mGoogleSignInClient) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        if (v==btn_custom_fb_sign_in){
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(EMAIL, PROFILE, BIRTHDAY, GENDER));
        }
        else{
            signIn(mGoogleSignInClient);
        }
    }
}
