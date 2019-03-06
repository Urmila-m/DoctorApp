package com.myapp.doctorapp.adapter;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.model.Doctor;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Doctor> list;
    Context context;

    public DoctorAdapter(List<Doctor> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.doctor_listview_adapter_layout, viewGroup, false);
        return (new ViewHolderDoctor(v));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Doctor doctor=list.get(i);
        ViewHolderDoctor h= (ViewHolderDoctor) viewHolder;
        h.tvName.setText(doctor.getName());
        h.tvRating.setText(Integer.toString(doctor.getRating()));
        h.tvHospital.setText(doctor.getHospital());
        h.tvSpeciality.setText(doctor.getSpeciality());

        Picasso.get().load(doctor.getImage()).placeholder(R.drawable.default_image).into(h.circleImageView);
        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO arko fragment load jasma doctor ko details
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


