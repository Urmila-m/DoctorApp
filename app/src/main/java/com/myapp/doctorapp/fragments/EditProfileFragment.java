package com.myapp.doctorapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;

public class EditProfileFragment extends Fragment implements View.OnClickListener {

    Button btnUpdate;
    NumberPicker height, weight, bloodGrp;
    String[] bloodGroup;
    private OnFragmentButtonClickListener listener;
    Bundle previousValues;
    String prevHeight, prevWeight, blood;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previousValues=getArguments();
        prevHeight=previousValues.getString("height");
        prevWeight=previousValues.getString("weight");
        blood=previousValues.getString("blood");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        height=view.findViewById(R.id.np_edit_height);
        weight=view.findViewById(R.id.np_edit_weight);
        bloodGrp=view.findViewById(R.id.np_blood_group);
        btnUpdate=view.findViewById(R.id.btn_edit_profile_update);

        height.setMinValue(0);
        height.setMaxValue(150);
        height.setValue(Integer.parseInt(prevHeight));
        height.setWrapSelectorWheel(false);

        weight.setMaxValue(100);
        weight.setMinValue(0);
        weight.setValue(Integer.parseInt(prevWeight));
        weight.setWrapSelectorWheel(false);

        bloodGroup= new String[]{"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        int intBlood = 0;
        for (int i=1;i<=8; i++){
            if (blood.equals(bloodGroup[i-1])){
                intBlood=i;
                break;
            }
        }
        bloodGrp.setMinValue(1);
        bloodGrp.setMaxValue(8);
        bloodGrp.setValue(intBlood);
        bloodGrp.setDisplayedValues(bloodGroup);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }

    }

    @Override
    public void onClick(View v) {
        int updatedWeight=weight.getValue();
        int updatedHeight=height.getValue();

        int intBlood=bloodGrp.getValue();
        String updatedBlood=bloodGroup[intBlood-1];

        Bundle bundle=new Bundle();
        bundle.putString("blood", updatedBlood);
        bundle.putString("height", updatedHeight+"");
        bundle.putString("weight", updatedWeight+"");

        listener.onButtonClicked(btnUpdate.getId(), new HomeFragment(), bundle);

    }
}
