package com.myapp.doctorapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class PreferenceInitializingActivity extends AppCompatActivity {

    protected SharedPreferences preferences;
    protected SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=getSharedPreferences("loggedIn", MODE_PRIVATE);
        editor=preferences.edit();
    }
}
