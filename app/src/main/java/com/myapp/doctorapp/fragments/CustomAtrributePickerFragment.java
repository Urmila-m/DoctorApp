package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.R;

public class CustomAtrributePickerFragment extends Fragment {

    private OnFragmentButtonClickListener listener;
    NumberPicker npWeight, npHeight, npGender;
    Button btnNext;
    String[] gender;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.custom_attribute_picker_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        npWeight=view.findViewById(R.id.np_weight);
        npHeight=view.findViewById(R.id.np_height);
        npGender=view.findViewById(R.id.np_gender);
        btnNext=view.findViewById(R.id.btn_custom_attribute_picker);

        npHeight.setMinValue(0);
        npHeight.setMaxValue(150);
        npHeight.setValue(100);
        npHeight.setWrapSelectorWheel(false);

        npWeight.setMaxValue(100);
        npWeight.setMinValue(0);
        npWeight.setValue(60);
        npWeight.setWrapSelectorWheel(false);

        gender= new String[]{"Male", "Female", "Others"};

        npGender.setDisplayedValues(gender);
        npGender.setMinValue(1);
        npGender.setMaxValue(3);
        npGender.setValue(2);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    int intGender=npGender.getValue();
                    int height=npHeight.getValue();
                    int weight=npWeight.getValue();

                    String strGender=gender[intGender-1];

                    Bundle bundle=new Bundle();
                    bundle.putString("gender", strGender);
                    bundle.putString("height", height+"");
                    bundle.putString("weight", weight+"");

                    listener.onButtonClicked(btnNext.getId(), new Fragment(), bundle);//finally logged in to the account
                }
            }
        });
    }
}
