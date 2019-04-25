package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.backgroundtasks.SignUpWithFacebookTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.User;

import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.SIGNUP;
import static com.myapp.doctorapp.Globals.addUserToPreference;
import static com.myapp.doctorapp.Globals.bundleToUser;

public class MainActivity extends PreferenceInitializingActivity implements View.OnClickListener, OnDataRetrievedListener {

    SignUpWithFacebookTask task;
    TextView tvSignUpEmailPhone;
    Button btnSignUp;
    ApiBackgroundTask apiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_options_layout);

            tvSignUpEmailPhone=findViewById(R.id.tv_sign_up_options_email_phone);
            btnSignUp = findViewById(R.id.btn_sign_up_options_fb);

            tvSignUpEmailPhone.setOnClickListener(this);
            btnSignUp.setOnClickListener(this);

            apiTask = new ApiBackgroundTask();
            task = new SignUpWithFacebookTask(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            task.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        if (v==btnSignUp){
            task.registerFbCallback();
        }

        else if(v==tvSignUpEmailPhone){
            startActivity(new Intent(MainActivity.this, BeforeLoginActivity.class));
        }
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        if (source.equals(API_INSERT)) {
            Log.e("TAG", "onDataRetrieved: Register successful: "+bundle.getString("errorMsg")+"!!!" );
        }
        else if (source.equals(SIGNUP)) {
            Log.e("Tag", "onDataRetrieved: " + bundle.getString("image"));
            apiTask.insertResponse(bundle, this);
            User user=bundleToUser(bundle);
            addUserToPreference(editor, user);
            Intent intent=new Intent(MainActivity.this, AfterLoginActivity.class);
            startActivity(intent);
            finish();

        }

    }
}
