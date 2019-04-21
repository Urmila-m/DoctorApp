package com.myapp.doctorapp.activity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.doctorapp.R;

public class MedicineActivity extends PreferenceInitializingActivity implements View.OnClickListener {

    EditText etMedicine;
    RadioButton beforeFood, afterFood;
    RadioGroup rgMedicine;
    CheckBox morning, day, night;
    Button btnSubmit;
    RatingBar doctorRating;
    TextView doctorName;
    String doctor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_doctor_rating_layout);

        Intent intent=getIntent();
        doctor=intent.getStringExtra("doctor");
        Log.e("TAG", "onCreate: Medicine Activity"+ doctor);
        doctorName=findViewById(R.id.tv_medicine_doctor_name);
        etMedicine=findViewById(R.id.et_medicine_name);
        beforeFood=findViewById(R.id.rb_before_food);
        afterFood=findViewById(R.id.rb_after_food);
        rgMedicine=findViewById(R.id.rg_medicine_food);
        morning=findViewById(R.id.rb_morning);
        day=findViewById(R.id.rb_day);
        night=findViewById(R.id.rb_night);
        doctorRating=findViewById(R.id.rating_bar_doctor);
        btnSubmit=findViewById(R.id.btn_medicine_submit);

        doctorName.setText(doctor);
        doctorRating.setRating(1);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String medicineName=etMedicine.getText().toString();
        String time=beforeFood.isChecked()?"before":"after";
        boolean boolMorning=morning.isChecked();
        boolean boolDay=day.isChecked();
        boolean boolnight=night.isChecked();
        float rating=doctorRating.getRating();

        if (medicineName.equals("")){
            Toast.makeText(this, "Please enter medicine name", Toast.LENGTH_LONG).show();
        }
        else {
            Intent intent = new Intent(this, AfterLoginActivity.class);
            intent.putExtra("medicine", medicineName);
            intent.putExtra("time", time);
            intent.putExtra("morning", boolMorning);
            intent.putExtra("day", boolDay);
            intent.putExtra("night", boolnight);
            intent.putExtra("rating", rating);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
        }
    }
}
