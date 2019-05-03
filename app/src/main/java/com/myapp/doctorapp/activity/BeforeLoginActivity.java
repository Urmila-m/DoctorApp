package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.fragments.ManualSignUpFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.User;
import com.myapp.doctorapp.services.NotificationService;

import java.security.AuthProvider;
import java.util.Set;

import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.VERIFY_USER;
import static com.myapp.doctorapp.Globals.addUserToPreference;
import static com.myapp.doctorapp.Globals.bundleToUser;

public class BeforeLoginActivity extends PreferenceInitializingActivity implements OnFragmentButtonClickListener, OnDataRetrievedListener {

    Bundle registrationInfo;
    ApiBackgroundTask apiTask;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        registrationInfo = new Bundle();
        apiTask = new ApiBackgroundTask();
        firebaseAuth=FirebaseAuth.getInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fl_before_login, new ManualSignUpFragment()).commit();
    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {

        Set<String> key=bundle.keySet();
        for (String s:key
        ) {
            registrationInfo.putString(s, bundle.getString(s));
        }

        if (id==R.id.btn_custom_attribute_picker){
            final User user=bundleToUser(registrationInfo);
            apiTask.insertResponse(registrationInfo, this);
            Log.e("TAG", "onButtonClicked: "+user.getEmail());
            firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.signInWithEmailAndPassword(user.getEmail(), user.getPassword());
                                Toast.makeText(BeforeLoginActivity.this, "Email registered successfully", Toast.LENGTH_SHORT).show();
                                firebaseAuth.getCurrentUser().sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.e("TAG", "onComplete: verify your email");
                                                startActivity(new Intent(BeforeLoginActivity.this, SignInActivity.class));

                                            }
                                        });

                            }

                            else {
                                Log.e("TAG", "onComplete: "+task.getException().getMessage());
                            }
                        }
                    });

        }

        else {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_before_login, fragment).commit();
        }

    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        if (source.equals(API_INSERT)){
            Log.e("TAG", "onDataRetrieved: registration successful"+bundle.getString("errorMsg"));

        }

        else if (source.equals(VERIFY_USER)){
            Log.e("TAG", "onDataRetrieved: user verified");
        }
    }
}
