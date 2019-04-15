package com.myapp.doctorapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.model.AppointmentDetail;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    List<AppointmentDetail> detail;
    Context context;

    public AppointmentAdapter(Context context, List<AppointmentDetail> detail) {
        this.detail = detail;
        this.context= context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.appointment_detail_recycle_view_layout, viewGroup, false);
        return (new MyAppointmentViewHolder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyAppointmentViewHolder h= (MyAppointmentViewHolder) viewHolder;
        h.doctor.setText(detail.get(i).getDoctor());
        h.date.setText(detail.get(i).getAppointment_date());
    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    private class MyAppointmentViewHolder extends RecyclerView.ViewHolder{

        TextView doctor;
        TextView date;

        public MyAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor=itemView.findViewById(R.id.tv_adapter_doctor_name);
            date=itemView.findViewById(R.id.tv_adapter_date);
        }
    }


}
