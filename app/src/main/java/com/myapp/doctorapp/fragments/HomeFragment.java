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

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

public class HomeFragment extends Fragment {

    OnFragmentButtonClickListener listener;
    LinearLayout findDoctorBlock;

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
        Log.e("TAG", "onAttach: "+context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findDoctorBlock=view.findViewById(R.id.find_doctor_block);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findDoctorBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: Home Fragment");
                listener.onButtonClicked(findDoctorBlock.getId(), new FindDoctorFragment(), new Bundle());
            }
        });
    }
}
