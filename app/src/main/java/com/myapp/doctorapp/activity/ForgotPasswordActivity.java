package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.EmailPasswordResponse;

import java.util.List;


public class ForgotPasswordActivity extends PreferenceInitializingActivity implements OnDataRetrievedListener {
    private EditText etEmail;
    private Button btnSendEmail;
    private ApiBackgroundTask apiTask;
    private String enteredEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);

        apiTask=new ApiBackgroundTask();

        etEmail=findViewById(R.id.et_forgot_password_email);
        btnSendEmail=findViewById(R.id.btn_forgot_password);
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enteredEmail=etEmail.getText().toString();
                Log.e("TAG", "onClick: "+enteredEmail);
                if (!isValidEmail(enteredEmail)){
                    Toast.makeText(ForgotPasswordActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
                }
                else {
                    apiTask.getEmailPassword(ForgotPasswordActivity.this);
                }
            }
        });
    }

    public boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        List<EmailPasswordResponse> responseList= (List<EmailPasswordResponse>) bundle.getSerializable("email_password_list");
        int count=0;
        for (EmailPasswordResponse response:responseList) {
            if ((enteredEmail.equals(response.getEmail()))&&(response.getPassword()!="")){
                Log.e("TAG", "onDataRetrieved: email registered");
                Intent intent=new Intent(ForgotPasswordActivity.this, SignInActivity.class);
                intent.putExtra("email", enteredEmail);
//                setResult(RESULT_OK, intent);
                count++;
                startActivity(intent);
                finish();
                break;
            }
        }
        if (count==0){
                Toast.makeText(this, "The email has not been registered", Toast.LENGTH_LONG).show();
        }
    }
}
