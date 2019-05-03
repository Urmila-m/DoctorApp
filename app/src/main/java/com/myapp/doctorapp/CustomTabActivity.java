package com.myapp.doctorapp;//used for test purpose

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.myapp.doctorapp.adapter.MedicineAdapter;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.MedicineDetails;

import java.util.ArrayList;
import java.util.List;

public class CustomTabActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_medicine_layout);
        tv=findViewById(R.id.tv_initial_medicine_layout);

        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO send dyanamic link
        Log.e("TAG", "onClick: ");
        ActionCodeSettings settings=ActionCodeSettings.newBuilder()
                .setAndroidPackageName("com.myapp.doctorapp", false, null)
                .setUrl("https://https://mydoctorapp.page.link")
                .setHandleCodeInApp(true)
                .build();

        FirebaseAuth.getInstance().sendPasswordResetEmail("urmi.mhrz@gmail.com", settings)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CustomTabActivity.this, "email sent", Toast.LENGTH_LONG).show();

                        }
                        else {
                            Exception e=task.getException();
                            Log.e("TAG", "onComplete: failed"+e.getMessage());
                            Toast.makeText(CustomTabActivity.this, "failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private String buildDynamicLink(){
        return "https://mydoctorapp.page.link/?"+
                "link="+
                "http://192.168.1.72"+
                "&apn="+
                "com.myapp.doctorapp";

    }
}
