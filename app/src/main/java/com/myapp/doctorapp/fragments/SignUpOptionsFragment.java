package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myapp.doctorapp.backgroundtasks.SignUpWithFacebookTask;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.R;

public class SignUpOptionsFragment extends Fragment implements View.OnClickListener {

    private OnFragmentButtonClickListener listener;
    private SignUpWithFacebookTask fbTask;
    TextView tvSignUpEmailPhone;
    Button btnSignUpFb;
    AppCompatActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_options_layout, container, false);
        fbTask =new SignUpWithFacebookTask(activity);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (AppCompatActivity) context;
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) activity;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbTask.getCallbackManager().onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onClick(View v) {
        if(v==tvSignUpEmailPhone){
            if (listener!=null) {
                listener.onButtonClicked(R.id.tv_sign_up_options_email_phone, new ManualSignUpFragment(), new Bundle());
            }
        }
        else if(v==btnSignUpFb){
            Log.e("SignUp", "onClick: Sign up with facebook clicked");
            fbTask.registerFbCallback();
            listener.onButtonClicked(btnSignUpFb.getId(), new Fragment(), new Bundle());
        }

    }
}
