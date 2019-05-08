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

    @Override
    public int getItemViewType(int position) {
        if (detail.size()==0){
            return 2;
        }
        else {
            return 1;
        }
    }

    public AppointmentAdapter(Context context, List<AppointmentDetail> detail) {
        this.detail = detail;
        this.context= context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder holder=null;
        if (i == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.appointment_detail_recycle_view_layout, viewGroup, false);
            holder=new MyAppointmentViewHolder(view);
        }
        else if (i==2){
            View view = LayoutInflater.from(context).inflate(R.layout.activity_dynamic_link, viewGroup, false);
            holder=new MedicineAdapter.EmptyViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MyAppointmentViewHolder) {
            MyAppointmentViewHolder h = (MyAppointmentViewHolder) viewHolder;
            h.doctor.setText(detail.get(i).getDoctor());
            h.date.setText(detail.get(i).getAppointment_date());
        }
        else {
            MedicineAdapter.EmptyViewHolder h= (MedicineAdapter.EmptyViewHolder) viewHolder;
            h.getTextView().setText("No Appointment records yet");
        }
    }

    @Override
    public int getItemCount() {
        if (detail.size() == 0) {
            return 1;
        } else {
            return detail.size();
        }
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
