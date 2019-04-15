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
import android.widget.DatePicker;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

public class DatePickerFragment extends Fragment {
    private OnFragmentButtonClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.date_picker_layout, container, false);
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
        final DatePicker datePicker=view.findViewById(R.id.dp_date_picker_layout);
        final Button btnNext=view.findViewById(R.id.btn_date_picker);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=datePicker.getYear();
                int month=datePicker.getMonth()+1;
                int day=datePicker.getDayOfMonth();

                Bundle bundle=new Bundle();
                bundle.putString("appointment_date", Integer.toString(month)+"/"+Integer.toString(day)+"/"+Integer.toString(year));
                if (listener!=null){
                    listener.onButtonClicked(btnNext.getId(), new TimePickerFragment(), bundle);
                }
            }
        });
    }
}
