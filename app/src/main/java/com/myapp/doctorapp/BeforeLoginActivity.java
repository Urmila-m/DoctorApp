package com.myapp.doctorapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.fragments.SignInFragment;
import com.myapp.doctorapp.fragments.SignUpOptionsFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

public class BeforeLoginActivity extends AppCompatActivity implements OnFragmentButtonClickListener, OnDataRetrievedListener {

    Fragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_before_login, new SignInFragment()).commit();
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
        }
        else{
            if(fragment instanceof SignUpOptionsFragment){
                myFragment=fragment;
            }
            Intent intent=new Intent(this, AfterLoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        Log.e("TAG", "Activity onDataRetrieved: ");
        String[] person=bundleToString(bundle);
        for (String s:person
             ) {
            Log.e("TAG", s+"\n");
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
