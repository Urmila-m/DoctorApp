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
//        Log.e("TAG", "MedicineAdapter: constructor");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.medicine_list_adapter_layout, viewGroup, false);
        MedicineViewHolder h=new MedicineViewHolder(view);
        return h;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MedicineViewHolder h= (MedicineViewHolder) viewHolder;
        h.tvDoctor.setText(medicineList.get(i).getDoctor());
        h.tvMedicine.setText(medicineList.get(i).getMedicine());
    }

    @Override
    public int getItemCount() {
//        Log.e("TAG", "getItemCount: adapter");
        return medicineList.size();
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder{
        TextView tvMedicine, tvDoctor;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMedicine=itemView.findViewById(R.id.tv_list_medicine);
            tvDoctor=itemView.findViewById(R.id.tv_list_doctor);
        }
    }
}
