package com.myapp.doctorapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class BeforeLoginActivity extends AppCompatActivity implements OnFragmentButtonClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_before_login, new SignInFragment()).commit();
        Log.e("TAG", "onCreate: before login activity");
    }

    @Override
    public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
        if(id!=R.id.btn_custom_attribute_picker){
            getSupportFragmentManager().beginTransaction().add(R.id.fl_before_login, fragment).commit();
        }
        else{
            Intent intent=new Intent(this, AfterLoginActivity.class);
            startActivity(intent);
        }
    }
}
