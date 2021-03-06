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
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment  extends Fragment implements View.OnClickListener {

    private OnFragmentButtonClickListener listener;
    TextView tvName, tvBlood, tvAge, tvWeight, tvHeight, tvGender;
    CircleImageView imageView;
    Bundle bundle;
    Button btnEditProfile;
    NetworkUtils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        utils=new NetworkUtils();
    }

    public ProfileFragment() {
        //required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user_profile_layout, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bundle=getArguments();
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName=view.findViewById(R.id.tv_profile_name);
        tvAge=view.findViewById(R.id.tv_age_profile);
        tvBlood=view.findViewById(R.id.tv_blood_profile);
        tvGender=view.findViewById(R.id.tv_gender_profile);
        tvHeight=view.findViewById(R.id.tv_height_profile);
        tvWeight=view.findViewById(R.id.tv_weight_profile);
        imageView=view.findViewById(R.id.iv_profile_image);
        btnEditProfile=view.findViewById(R.id.btn_edit_profile);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        String birthday=Integer.toString(calculateAge(bundle.getString("birthday")));
        super.onActivityCreated(savedInstanceState);
        tvName.setText(bundle.getString("name"));
        tvWeight.setText(bundle.getString("weight"));
        tvHeight.setText(bundle.getString("height"));
        tvGender.setText(bundle.getString("gender"));
        tvBlood.setText(bundle.getString("blood"));
        tvAge.setText(birthday);
        btnEditProfile.setOnClickListener(this);

        Picasso.get().load(bundle.getString("image")).placeholder(R.drawable.default_image).into(imageView);
    }

    int calculateAge(String birthday) {

        Log.e("TAG", "calculateAge: birthday"+birthday );
        SimpleDateFormat sDF=new SimpleDateFormat("MM/dd/yyyy");
        Date birthDate= new Date();
        try {
            birthDate = sDF.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar currentDate=Calendar.getInstance();
        Calendar dOB=Calendar.getInstance();
        dOB.setTime(birthDate);

        long ageInMilliSec=currentDate.getTimeInMillis()-dOB.getTimeInMillis();
        long m=ageInMilliSec/(1000*60);
        int age= (int) (m/(60*24*365));
        return age;
    }

    @Override
    public void onClick(View v) {
        if (utils.isNetworkConnected(getContext())){
            listener.onButtonClicked(btnEditProfile.getId(), new EditProfileFragment(), new Bundle());
        }
        else {
            Toast.makeText(getContext(), "No internet Connection!!", Toast.LENGTH_LONG).show();
        }
    }
}
