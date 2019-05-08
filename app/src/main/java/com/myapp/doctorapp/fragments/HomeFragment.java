package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.utils.NetworkUtils;

public class HomeFragment extends Fragment implements View.OnClickListener {

    OnFragmentButtonClickListener listener;
    LinearLayout findDoctorBlock;
    LinearLayout appoinmentBlock;
    LinearLayout medicineBlock;
    Context context;
    NetworkUtils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils=new NetworkUtils();
    }

    public HomeFragment() {
        //required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.options_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findDoctorBlock=view.findViewById(R.id.find_doctor_block);
        appoinmentBlock=view.findViewById(R.id.home_appointment);
        medicineBlock=view.findViewById(R.id.my_medicine_block);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findDoctorBlock.setOnClickListener(this);
        appoinmentBlock.setOnClickListener(this);
        medicineBlock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (utils.isNetworkConnected(getContext())) {
            if (v == findDoctorBlock) {
                listener.onButtonClicked(findDoctorBlock.getId(), new FindDoctorFragment(), new Bundle());
            } else if (v == appoinmentBlock) {
                listener.onButtonClicked(R.id.home_appointment, new MyAppointmentFragment(), new Bundle());
            } else if (v == medicineBlock) {
                listener.onButtonClicked(R.id.my_medicine_block, new MyMedicineFragment(), new Bundle());
            }
        }

        else {
            Toast.makeText(context, "No internet connection!!", Toast.LENGTH_SHORT).show();
        }
    }
}
