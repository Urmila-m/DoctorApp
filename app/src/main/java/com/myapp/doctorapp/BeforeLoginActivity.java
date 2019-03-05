package com.myapp.doctorapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.fragments.CustomAtrributePickerFragment;
import com.myapp.doctorapp.fragments.SignInFragment;
import com.myapp.doctorapp.fragments.SignUpOptionsFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

import java.util.Set;

public class BeforeLoginActivity extends AppCompatActivity implements OnFragmentButtonClickListener, OnDataRetrievedListener {

    private static final String API="ApiBackgroundTask";
    private static final String SIGNUP="SignUpWithFacebookTask";

    Fragment myFragment;
    Bundle registrationInfo;
    ApiBackgroundTask apiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        registrationInfo=new Bundle();
        apiTask=new ApiBackgroundTask();
        SignInFragment signInFragment=new SignInFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_before_login, signInFragment).commit();
        Log.e("TAG", "onCreate: before login activity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myFragment.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
        if(id!=R.id.btn_custom_attribute_picker && id!=R.id.btn_sign_up_options_fb){
            getSupportFragmentManager().beginTransaction().add(R.id.fl_before_login, fragment).commit();
            Set<String> key=bundle.keySet();
            for (String s:key
            ) {
                registrationInfo.putString(s, bundle.getString(s));
            }
        }
        else{
            if(fragment instanceof SignUpOptionsFragment){
                myFragment=fragment;
            }
            Intent intent=new Intent(BeforeLoginActivity.this, AfterLoginActivity.class);
            if(id==R.id.btn_custom_attribute_picker){
                Set<String> key=bundle.keySet();
                for (String s:key
                ) {
                    registrationInfo.putString(s, bundle.getString(s));
                }
                intent.putExtra("data", registrationInfo);
            }
            apiTask.insertResponse(registrationInfo, this);
            startActivity(intent);
        }
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
       if (source.equals(API)){
           Log.e("TAG", "onDataRetrieved: registration successful"+bundle.getString("errorMsg"));
       }
    }

    public String[] bundleToString(Bundle bundle){
        String id=bundle.getString("id");
        String name=bundle.getString("name");
        String gender=bundle.getString("gender");
        String bday=bundle.getString("birthday");
        String email=bundle.getString("email");
        String image=bundle.getString("image");
        String[] person={id, name, gender, bday, email, image};
        return person;

    }
}
