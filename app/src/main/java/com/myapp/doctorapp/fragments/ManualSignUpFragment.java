package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.R;

public class ManualSignUpFragment extends Fragment {

    OnFragmentButtonClickListener listener;
    EditText etName, etEmail, etMobile, etPass, etConfirmPass;
    Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sign_up_form_layout, container, false);
        return view;
    }

    boolean isValidEmail(String email){
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
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
        etName=view.findViewById(R.id.et_sign_up_form_name);
        etEmail=view.findViewById(R.id.et_sign_up_form_email);
        etMobile=view.findViewById(R.id.et_sign_up_form_mobile);
        etPass=view.findViewById(R.id.et_sign_up_form_password);
        etConfirmPass=view.findViewById(R.id.et_sign_up_form_confirm_pass);
        btnSubmit=view.findViewById(R.id.btn_sign_up_form);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                Bundle bundle=new Bundle();
                String name=etName.getText().toString();
                String email=etEmail.getText().toString();
                String mobile=etMobile.getText().toString();
                String password=etPass.getText().toString();
                String confirmPass=etConfirmPass.getText().toString();

                bundle.putString("name", name);
                bundle.putString("email", email);
                bundle.putString("mobile", mobile);
                bundle.putString("password", password);

                if (name.equals("")){
                    Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    count++;
                }
                if (mobile.length()!=10){
                    Toast.makeText(getContext(), "Incorrect Mobile Number", Toast.LENGTH_SHORT).show();
                    count++;
                }
                if(!password.equals(confirmPass)){
                    Toast.makeText(getContext(), "Password incorrect", Toast.LENGTH_SHORT).show();
                    count++;
                }

                if (password.length()<8){
                    Toast.makeText(getContext(), "Password should at least contain 8 characters", Toast.LENGTH_SHORT);
                    count++;
                }
                if (!isValidEmail(email)){
                    Toast.makeText(getContext(), "Invalid email!!!", Toast.LENGTH_SHORT).show();
                    count++;
                }

                if(count==0){
                    if (listener!=null) {
                        listener.onButtonClicked(R.id.btn_sign_up_form, new BirthdayPickerFragment(), bundle);
                    }
                }


            }
        });
    }
}
