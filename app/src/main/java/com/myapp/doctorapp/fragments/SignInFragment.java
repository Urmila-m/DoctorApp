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
import android.widget.TextView;

import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.R;

public class SignInFragment extends Fragment implements View.OnClickListener {

    OnFragmentButtonClickListener listener;
    TextView tvCreateAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sign_in_layout, container, false);
        Log.e("TAG", "onCreateView: sign in fragment");
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
        tvCreateAccount =view.findViewById(R.id.tv_sign_in_create_account);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tvCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v== tvCreateAccount){
            if (listener!=null) {
                listener.onButtonClicked(R.id.tv_sign_in_create_account, new SignUpOptionsFragment(), new Bundle());
            }
        }
    }
}
