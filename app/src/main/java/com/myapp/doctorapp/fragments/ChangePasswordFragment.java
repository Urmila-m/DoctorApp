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
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {

    private EditText etOldPass, etNewPass, etConfirmNewPass;
    private Button btnSaveChanges;
    String currentPassword;
    OnFragmentButtonClickListener listener;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context!=null){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getArguments();
        currentPassword=b.getString("currentPassword");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.change_password_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etOldPass=view.findViewById(R.id.et_old_password);
        etNewPass=view.findViewById(R.id.et_new_password);
        etConfirmNewPass=view.findViewById(R.id.et_retype_new_password);
        btnSaveChanges=view.findViewById(R.id.btn_change_password_save);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=new Bundle();
                if (!etNewPass.getText().toString().equals(etConfirmNewPass.getText().toString())){
                    Toast.makeText(getContext(), "New password doesnt match!!", Toast.LENGTH_SHORT).show();
                }
                else if (!currentPassword.equals(etOldPass.getText().toString())){
                    Toast.makeText(getContext(), "Current password doesnt match!!", Toast.LENGTH_LONG).show();
                }
                else {
                    if(etNewPass.getText().toString().length()<8){
                        Toast.makeText(getContext(), "Password should be at least 8 characters long!!", Toast.LENGTH_LONG).show();
                    }
                    else {
//                        b.putString("oldPassword", etOldPass.getText().toString());
                        b.putString("newPassword", etNewPass.getText().toString());
                        listener.onButtonClicked(btnSaveChanges.getId(), new HomeFragment(), b);
                    }
                }
            }
        });

    }
}
