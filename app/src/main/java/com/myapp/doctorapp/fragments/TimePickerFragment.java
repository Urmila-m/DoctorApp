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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

public class TimePickerFragment extends Fragment{
    private OnFragmentButtonClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.time_picker_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TimePicker timePicker=view.findViewById(R.id.tp_time_picker_layout);
        final Button btnNext=view.findViewById(R.id.btn_choose_time);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour=timePicker.getHour();
                int minute=timePicker.getMinute();

                Bundle bundle=new Bundle();
                bundle.putString("appointment_time", String.format("%02d", hour)+":"+minute);
                Log.e("TAG", "onClick: "+bundle.getString("appointment_time"));
                if (listener!=null){
                    listener.onButtonClicked(btnNext.getId(), new HomeFragment(), bundle);
                }
            }
        });
    }
}
