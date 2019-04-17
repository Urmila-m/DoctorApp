package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.backgroundtasks.CheckFbRegistrationTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.IdModel;
import com.myapp.doctorapp.model.User;
import com.myapp.doctorapp.services.NotificationService;

import java.util.ArrayList;
import java.util.List;

import static com.myapp.doctorapp.Globals.API_GET_EMAIL;
import static com.myapp.doctorapp.Globals.API_GET_ID_LIST;
import static com.myapp.doctorapp.Globals.API_GET_USER;
import static com.myapp.doctorapp.Globals.CHECK_REGISTRATION;
import static com.myapp.doctorapp.Globals.GET_USER_USING_ID;
import static com.myapp.doctorapp.Globals.addUserToPreference;

public class SignInActivity extends PreferenceInitializingActivity implements View.OnClickListener, OnDataRetrievedListener {

    TextView tvCreateAccount;
    Button btnManualSignIn;
    ImageView signInFb;
    EditText etEmail;
    EditText etPassword;
    String email, password;
    String activeId;

    ApiBackgroundTask apiTask;
    CheckFbRegistrationTask checkFbRegistrationTask;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        boolean isLogin=accessToken!=null&&!accessToken.isExpired();

        if (isLogin){
            startActivity(new Intent(this, AfterLoginActivity.class));
            finish();
        }

        String preferenceEmpty=preferences.getString("email", "");
        if (!preferenceEmpty.equals("")){
            startActivity(new Intent(this, AfterLoginActivity.class));
            finish();
        }

        tvCreateAccount =findViewById(R.id.tv_sign_in_create_account);
        btnManualSignIn=findViewById(R.id.btn_manual_sign_in);
        signInFb=findViewById(R.id.iv_sign_in_fb);
        etEmail=findViewById(R.id.et_email_sign_in);
        etPassword=findViewById(R.id.et_password_sign_in);

        tvCreateAccount.setOnClickListener(this);
        btnManualSignIn.setOnClickListener(this);
        signInFb.setOnClickListener(this);

        apiTask=new ApiBackgroundTask();
        checkFbRegistrationTask=new CheckFbRegistrationTask(this);
    }

    @Override
    public void onClick(View v) {
        if (v== tvCreateAccount){
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
        else if(v==btnManualSignIn){
            email=etEmail.getText().toString();
            password=etPassword.getText().toString();
            if (password.equals("")){
                Log.e("TAG", "onButtonClicked: Fill the password!!");
            }
            else {
                Log.e("TAG", "onButtonClicked: " + email);
                apiTask.getEmailPassword(this);
            }
        }
        else if(v==signInFb){
            Log.e("TAG", "onClick: sign in with fb clicked");
            //TODO FacebookTask chainxa, tyabata aako email ra database ma vako email compare garne, milyo vane afterloginActivity
            checkFbRegistrationTask.registerFbCallback();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkFbRegistrationTask.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        if (source.equals(API_GET_EMAIL)){
            Log.e("TAG", "onDataRetrieved: source api get email");
            int count=0;
            List<EmailPasswordResponse> list= (List<EmailPasswordResponse>) bundle.getSerializable("email_password_list");
            for (EmailPasswordResponse response: list
            ) {
                if(response.getEmail().equals(email)&&response.getPassword().equals(password)){
                    apiTask.getUser(email, this);//TODO email register vako xa xaina xuttai check garne ani password
                    count++;
                    break;
                }
            }
            if (count==0){
                Log.e("TAG", "onDataRetrieved: "+email+" doesnt have account yet!! or password incorrect");
            }
        }

        else if (source.equals(API_GET_USER)){
            User user= (User) bundle.getSerializable("User");
            addUserToPreference(editor, user);
            startActivity(new Intent(SignInActivity.this, AfterLoginActivity.class));
            finish();
        }

        else if(source.equals(CHECK_REGISTRATION)){
            activeId=bundle.getString("id");
            apiTask.getIdList(this);
        }

        else if ((source.equals(API_GET_ID_LIST))){
            int count=0;
            List<IdModel> idList= (List<IdModel>) bundle.getSerializable("id_list");

            for (IdModel id:idList
                 ) {
                if (activeId.equals(id.getId())){
                    apiTask.getUserUsingId(activeId, this);
                    count++;
                    break;
                }
            }
            if (count==0){
                Log.e("TAG", "onDataRetrieved: havent registered yet");
            }
        }

        else if (source.equals(GET_USER_USING_ID)){
            User user= (User) bundle.getSerializable("user");
            addUserToPreference(editor, user);
            startActivity(new Intent(SignInActivity.this, AfterLoginActivity.class));
            finish();


        }
    }
}
