package com.myapp.doctorapp.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.WindowManager;
import android.widget.Toast;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.fragments.DatePickerFragment;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.Doctor;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.myapp.doctorapp.Globals.ALERT_POP_UP;

public class DoctorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    OnFragmentButtonClickListener listener;
    List<Doctor> list;
    Context context;

    public DoctorAdapter(List<Doctor> list, Context context) {
        this.list = list;
        this.context = context;
        if (context instanceof OnFragmentButtonClickListener){
            listener= (OnFragmentButtonClickListener) context;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_listview_adapter_layout, viewGroup, false);
        return (new ViewHolderDoctor(v));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Doctor doctor=list.get(i);
        ViewHolderDoctor h= (ViewHolderDoctor) viewHolder;
        h.tvName.setText(doctor.getName());
        h.tvRating.setText(Integer.toString(doctor.getRating()));
        h.tvHospital.setText(doctor.getHospital());
        h.tvSpeciality.setText(doctor.getSpeciality());

        Picasso.get().load(doctor.getImage()).placeholder(R.drawable.default_image).into(h.circleImageView);
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View doctorDetails=LayoutInflater.from(context).inflate(R.layout.doctor_information_layout, null);
                TextView tvName=doctorDetails.findViewById(R.id.tv_name_doctor_info);
                TextView tvRating=doctorDetails.findViewById(R.id.tv_rating_doctor_info);
                TextView tvSpeciality=doctorDetails.findViewById(R.id.tv_speciality_doctor_info);
                TextView tvHospital=doctorDetails.findViewById(R.id.tv_hospital_doctor_info);
                CircleImageView image=doctorDetails.findViewById(R.id.profile_image_doctor_info);
                final LinearLayout call=doctorDetails.findViewById(R.id.call_doctor_info);
                Button bookAppointment=doctorDetails.findViewById(R.id.btn_book_appointment_doctor_info);

                tvName.setText(doctor.getName());
                tvRating.setText(Integer.toString(doctor.getRating()));
                tvHospital.setText(doctor.getHospital());
                tvSpeciality.setText(doctor.getSpeciality());

                Picasso.get().load(doctor.getImage()).placeholder(R.drawable.default_image).into(image);

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (context instanceof OnDataRetrievedListener){
                            String phone=doctor.getPhone();
                            Bundle bundle=new Bundle();
                            bundle.putString("phone", phone);
                            ((OnDataRetrievedListener) context).onDataRetrieved(ALERT_POP_UP, bundle);
                        }
                    }
                });

                DisplayMetrics displayMetrics = new DisplayMetrics();
                WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//if in activity, directly getWindowManager() can be called
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);//sets the values of the screen size in displayMetrics object
                int width=displayMetrics.widthPixels;//getting full window width
                int height=displayMetrics.heightPixels;//getting full window height
                width*=0.9;//will be used to set the width of alert dialog
                height*=0.7;

                AlertDialog.Builder builder=new AlertDialog.Builder(context)
                        .setView(doctorDetails);

                final AlertDialog alertDialog=builder.create();

                alertDialog.show();
                alertDialog.getWindow().setLayout(width, height);

                bookAppointment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle=new Bundle();
                        bundle.putString("doctorName", doctor.getName());
                        Toast.makeText(context, "book button clicked", Toast.LENGTH_SHORT).show();
                        listener.onButtonClicked(R.id.btn_book_appointment_doctor_info, new DatePickerFragment(), bundle);
                        alertDialog.dismiss();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderDoctor extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView tvName;
        TextView tvHospital;
        TextView tvSpeciality;
        TextView tvRating;

        public ViewHolderDoctor(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.iv_doctor_adapter);
            tvName=itemView.findViewById(R.id.tv_doctor_adapter_name);
            tvHospital=itemView.findViewById(R.id.tv_doctor_adapter_hospital);
            tvRating=itemView.findViewById(R.id.tv_doctor_adapter_rating);
            tvSpeciality=itemView.findViewById(R.id.tv_doctor_adapter_speciality);
        }
    }
}


