package com.myapp.doctorapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.backgroundtasks.CheckFbRegistrationTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.EmailPasswordResponse;
import com.myapp.doctorapp.model.IdModel;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.model.User;
import com.myapp.doctorapp.model.VerificationResponse;
import com.myapp.doctorapp.services.NotificationService;
import com.myapp.doctorapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import static com.myapp.doctorapp.Globals.API_GET_EMAIL;
import static com.myapp.doctorapp.Globals.API_GET_ID_LIST;
import static com.myapp.doctorapp.Globals.API_GET_USER;
import static com.myapp.doctorapp.Globals.CHECK_REGISTRATION;
import static com.myapp.doctorapp.Globals.CHECK_VERIFICATION;
import static com.myapp.doctorapp.Globals.GET_USER_USING_ID;
import static com.myapp.doctorapp.Globals.VERIFY_USER;
import static com.myapp.doctorapp.Globals.addUserToPreference;

public class SignInActivity extends PreferenceInitializingActivity implements View.OnClickListener, OnDataRetrievedListener {

    TextView tvCreateAccount;
    TextView tvForgotPassword;
    Button btnManualSignIn;
    ImageView signInFb;
    EditText etEmail;
    TextInputEditText etPassword;
    String email, password, email2;
    String activeId;
    User user2;
    ProgressDialog dialog;
    NetworkUtils utils;

    ApiBackgroundTask apiTask;
    CheckFbRegistrationTask checkFbRegistrationTask;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_layout);

        apiTask=new ApiBackgroundTask();
        utils=new NetworkUtils();
        dialog=new ProgressDialog(this);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null ){
            Log.e("TAG", "onCreate: User not null"+firebaseUser.getEmail());
            firebaseUser.reload()
                    //isEmailVerified() returns false even after it is clicked if not reloaded
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    FirebaseUser reloadedUser=FirebaseAuth.getInstance().getCurrentUser();
                    if (reloadedUser.isEmailVerified()){
                        Log.e("TAG", "onCreate: "+reloadedUser.getEmail());
                        apiTask.verifyUser(reloadedUser.getEmail(), SignInActivity.this);
                        FirebaseAuth.getInstance().signOut();
                    }
                }
            });
        }

//        Intent intent=getIntent();
//        if (intent.getStringExtra("email")!=null){
//            Log.e("TAG", "onCreate: intent not empty");
//            email2=intent.getStringExtra("email");
//            editor.putString("email2", "").commit();
//            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(SignInActivity.this, "Password reset link has been sent to the email", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Log.e("TAG", "onComplete: email with password reset link sending failed!!");
//                            }
//                        }
//                    });
//        }

//        if (preferences.getString("email2", "")!="") {//TODO dont know how to do.will need dynamic links
//            FirebaseAuth.getInstance().verifyPasswordResetCode(preferences.getString("email2", ""))
//                    .addOnCompleteListener(new OnCompleteListener<String>() {
//                        @Override
//                        public void onComplete(@NonNull Task<String> task) {
//                            if (task.isSuccessful()) {
//                                Log.e("TAG", "onComplete: Verified");
//                                editor.remove("email2").commit();
//                            }
//                        }
//                    });
//        }

//        FirebaseAuth.getInstance().confirmPasswordReset();

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
        tvForgotPassword=findViewById(R.id.tv_sign_in_forgot_password);

        tvCreateAccount.setOnClickListener(this);
        btnManualSignIn.setOnClickListener(this);
        signInFb.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);

        checkFbRegistrationTask=new CheckFbRegistrationTask(this);
    }

    @Override
    public void onClick(View v) {
        if (utils.isNetworkConnected(SignInActivity.this)) {
            Log.e("TAG", "onClick: Network connected" );
            if (v == tvCreateAccount) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            } else if (v == btnManualSignIn) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                if (password.equals("")) {
                    Log.e("TAG", "onButtonClicked: Fill the password!!");
                } else {
                    Log.e("TAG", "onButtonClicked: " + email);
                    dialog.setTitle("\t Signing in");
                    dialog.show();
                    apiTask.getEmailPassword(this);
                }
            } else if (v == signInFb) {
                Log.e("TAG", "onClick: sign in with fb clicked");
                checkFbRegistrationTask.registerFbCallback();

            } else if (v == tvForgotPassword) {
                Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        }

        else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
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
            user2= (User) bundle.getSerializable("User");
            Log.e("TAG", "onDataRetrieved: "+user2.toString());
            apiTask.checkVerification(user2.getEmail(), this);

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
                    dialog.show();
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
            dialog.dismiss();
            startActivity(new Intent(SignInActivity.this, AfterLoginActivity.class));
            finish();
        }

        else if (source.equals(CHECK_VERIFICATION)){
            VerificationResponse response= (VerificationResponse) bundle.getSerializable("verified");
            if (response.getVerified()==1){
                addUserToPreference(editor, user2);
                startActivity(new Intent(SignInActivity.this, AfterLoginActivity.class));
                dialog.dismiss();
                finish();

            }

            else {
                Toast.makeText(this, "User is not verified", Toast.LENGTH_SHORT).show();
            }
        }

        else if (source.equals(VERIFY_USER)){
            PostResponse response= (PostResponse) bundle.getSerializable("response");
            Log.e("TAG", "onDataRetrieved: "+response.getErrorMsg());
        }
    }
}
