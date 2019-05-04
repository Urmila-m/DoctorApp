package com.myapp.doctorapp.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.adapter.PhotosAdapter;
import com.myapp.doctorapp.interfaces.OnFragmentButtonClickListener;
import com.myapp.doctorapp.model.ImageUrlModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhotosFragment extends Fragment {

    RecyclerView recyclerView;
    PhotosAdapter adapter;
    List<ImageUrlModel> list;
    RecyclerView.LayoutManager manager;

    public MyPhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        list= (List<ImageUrlModel>) bundle.getSerializable("listOfAllImageUrl");
        manager=new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        adapter=new PhotosAdapter(list, getContext(), new OnFragmentButtonClickListener() {
            @Override
            public void onButtonClicked(int id, Fragment fragment, Bundle bundle) {
                //TODO send to AfterActivity
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.find_doctor_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rv_doctor_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
