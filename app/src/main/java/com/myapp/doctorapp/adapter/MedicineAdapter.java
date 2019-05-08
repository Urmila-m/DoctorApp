package com.myapp.doctorapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.model.MedicineDetails;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter {
    List<MedicineDetails> medicineList;
    Context context;

    public MedicineAdapter(List<MedicineDetails> medicineList, Context context) {
        this.medicineList = medicineList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (medicineList.size()==0){
            return 2;
        }
        else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder h = null;
        if (i==1) {
            View view = LayoutInflater.from(context).inflate(R.layout.medicine_list_adapter_layout, viewGroup, false);
            h = new MedicineViewHolder(view);
        }
        else if (i==2){
            View view=LayoutInflater.from(context).inflate(R.layout.activity_dynamic_link, viewGroup, false);
            h=new EmptyViewHolder(view);
        }
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MedicineViewHolder) {
            Log.e("TAG", "onBindViewHolder: i=1");
            MedicineViewHolder h = (MedicineViewHolder) viewHolder;
            h.tvDoctor.setText(medicineList.get(i).getDoctor());
            h.tvMedicine.setText(medicineList.get(i).getMedicine());
        }
        else if (viewHolder instanceof EmptyViewHolder){
            Log.e("TAG", "onBindViewHolder: i=2");
            EmptyViewHolder h= (EmptyViewHolder) viewHolder;
            h.textView.setText("No medicine records yet");
        }
    }

    @Override
    public int getItemCount() {
        if (medicineList.size()==0){
            return 1;
        }
        else {
            return medicineList.size();
        }
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder{
        TextView tvMedicine, tvDoctor;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicine=itemView.findViewById(R.id.tv_list_medicine);
            tvDoctor=itemView.findViewById(R.id.tv_list_doctor);
        }
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.tv_dynamic_link);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
