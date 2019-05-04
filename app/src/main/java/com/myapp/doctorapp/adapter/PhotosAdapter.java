package com.myapp.doctorapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.ImageUrlModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter {

    private List<ImageUrlModel> list;
    private Context context;
    private OnFragmentButtonClickListener listener;

    public PhotosAdapter(List<ImageUrlModel> list, Context context, OnFragmentButtonClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.photos_adapter_layout, viewGroup, false);
        return new PhotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        PhotosViewHolder h= (PhotosViewHolder) viewHolder;
        Picasso.get().load(list.get(i).getImageUrl()).placeholder(R.drawable.default_image).into(h.imageView);
        if (listener!=null){
            h.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onButtonClicked(R.id.iv_photos_adapter, new Fragment(), new Bundle());//TODO show overlay option to make the photo profile picture
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class PhotosViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public PhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_photos_adapter);
        }
    }
}
