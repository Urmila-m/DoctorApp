package com.myapp.doctorapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;

public class CustomAttributePickerActivity extends AppCompatActivity{
    NumberPicker npWeight, npHeight, npGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_attribute_picker_layout);
        npWeight=findViewById(R.id.np_weight);
        npHeight=findViewById(R.id.np_height);
        npGender=findViewById(R.id.np_gender);

        npHeight.setMinValue(50);
        npHeight.setMaxValue(150);
        npHeight.setValue(100);
        npHeight.setWrapSelectorWheel(false);

        npWeight.setMaxValue(80);
        npWeight.setMinValue(40);
        npWeight.setValue(60);
        npWeight.setWrapSelectorWheel(false);

        String[] gender={"Male", "Female", "Others"};

        npGender.setDisplayedValues(gender);
        npGender.setMinValue(1);
        npGender.setMaxValue(3);
        npGender.setValue(2);
    }
}
