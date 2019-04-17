package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.fragments.ManualSignUpFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.User;
import com.myapp.doctorapp.services.NotificationService;

import java.util.Set;

import static com.myapp.doctorapp.Globals.API_INSERT;
import static com.myapp.doctorapp.Globals.addUserToPreference;
import static com.myapp.doctorapp.Globals.bundleToUser;

public class BeforeLoginActivity extends PreferenceInitializingActivity implements OnFragmentButtonClickListener, OnDataRetrievedListener {

    Bundle registrationInfo;
    ApiBackgroundTask apiTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_login);

        registrationInfo = new Bundle();
        apiTask = new ApiBackgroundTask();

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
            User user=bundleToUser(registrationInfo);
            addUserToPreference(editor, user);
            apiTask.insertResponse(registrationInfo, this);
            startActivity(new Intent(BeforeLoginActivity.this, AfterLoginActivity.class));
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
    }
}
