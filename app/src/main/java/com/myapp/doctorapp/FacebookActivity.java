package com.myapp.doctorapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;

import com.myapp.doctorapp.model.Doctor;

import java.util.ArrayList;
import java.util.List;

public class FacebookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DoctorAdapter adapter;
    List<Doctor> list;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_doctor_fragment_layout);
        recyclerView=findViewById(R.id.rv_doctor_list);
        list=new ArrayList<>();
        layoutManager=new LinearLayoutManager(this);

        Doctor doctor=new Doctor("Rajesh", "https://i.imgur.com/tGbaZCY.jpg", "Patan Hospital, Lagankhel","ENT", 12,4 );
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        list.add(doctor);
        adapter=new DoctorAdapter(list, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
