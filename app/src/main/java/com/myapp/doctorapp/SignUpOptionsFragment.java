package com.myapp.doctorapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignUpOptionsFragment extends Fragment implements View.OnClickListener {

    private OnFragmentButtonClickListener listener;
    TextView tvSignUpEmailPhone;
    Button btnSignUpFb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sign_up_options_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) getActivity();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSignUpEmailPhone=view.findViewById(R.id.tv_sign_up_options_email_phone);
        btnSignUpFb=view.findViewById(R.id.btn_sign_up_options_fb);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvSignUpEmailPhone.setOnClickListener(this);
        btnSignUpFb.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==tvSignUpEmailPhone){
            if (listener!=null) {
                listener.onButtonClicked(R.id.tv_sign_up_options_email_phone, new ManualSignUpFragment(), new Bundle());
            }
        }
        else if(v==btnSignUpFb){
            //TODO sign up with facebook wala sab code
        }

    }
}
