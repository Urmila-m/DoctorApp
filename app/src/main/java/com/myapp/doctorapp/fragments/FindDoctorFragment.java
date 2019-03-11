package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapp.doctorapp.adapter.DoctorAdapter;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.model.Doctor;

import java.util.List;

public class FindDoctorFragment extends Fragment {

    DoctorAdapter adapter;
    Context context;
    List<Doctor> list;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Bundle bundle;

    public FindDoctorFragment() {
        //required empty constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle=getArguments();
        list= (List<Doctor>) bundle.getSerializable("doctor_list");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.find_doctor_fragment_layout, container, false);
        layoutManager=new LinearLayoutManager(context);
        adapter=new DoctorAdapter(list, context);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rv_doctor_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
