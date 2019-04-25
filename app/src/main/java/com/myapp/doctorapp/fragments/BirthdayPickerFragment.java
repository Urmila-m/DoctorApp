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

import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.R;

public class BirthdayPickerFragment extends Fragment {

    private OnFragmentButtonClickListener listener;
    DatePicker datePicker;
    Button btnNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.birthday_selector_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker=view.findViewById(R.id.dp_bday_picker);
        btnNext=view.findViewById(R.id.btn_bday_picker_next);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) getActivity();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    int day=datePicker.getDayOfMonth();
                    int month=datePicker.getMonth()+1;
                    int year=datePicker.getYear();
                    Bundle bundle=new Bundle();
                    String bday=String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+String.format("%04d", year);
                    bundle.putString("birthday", bday);
                    listener.onButtonClicked(R.id.btn_bday_picker_next, new CustomAtrributePickerFragment(), bundle);
                }
            }
        });

    }
}
