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

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.adapter.AppointmentAdapter;
import com.myapp.doctorapp.model.AppointmentDetail;

import java.util.ArrayList;
import java.util.List;

public class MyAppointmentFragment extends Fragment {

    RecyclerView recyclerView;
    AppointmentAdapter adapter;
    List<AppointmentDetail> list;
    RecyclerView.LayoutManager manager;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.find_doctor_fragment_layout, container, false);
        adapter=new AppointmentAdapter(getContext(), list);
        manager=new LinearLayoutManager(getContext());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bundle=getArguments();
        list= (List<AppointmentDetail>) bundle.getSerializable("DetailList");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rv_doctor_list);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }


}
