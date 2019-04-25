package com.myapp.doctorapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.myapp.doctorapp.adapter.MedicineAdapter;
import com.myapp.doctorapp.backgroundtasks.ApiBackgroundTask;
import com.myapp.doctorapp.interfaces.OnDataRetrievedListener;
import com.myapp.doctorapp.model.MedicineDetails;

import java.util.ArrayList;
import java.util.List;

public class CustomTabActivity extends AppCompatActivity implements OnDataRetrievedListener {

    ApiBackgroundTask apiTask;
    RecyclerView rv;
    MedicineAdapter adapter;
    List<MedicineDetails> list;
    RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_doctor_fragment_layout);

        rv=findViewById(R.id.rv_doctor_list);
        manager=new LinearLayoutManager(this);
//        list=new ArrayList<>();
//        MedicineDetails d=new MedicineDetails("sample", "doctor",
//                "time", "medicine", false,
//                false, false,
//                3);
//        list.add(d);
//        list.add(d);
//        list.add(d);
//        list.add(d);
//        list.add(d);
        apiTask=new ApiBackgroundTask();
        apiTask.getMyMedicine("Urmila Maharjan", this);
//        adapter=new MedicineAdapter(list, this);
//        rv.setLayoutManager(manager);
//        rv.setAdapter(adapter);

    }

    @Override
    public void onDataRetrieved(String source, Bundle bundle) {
        for(String s:bundle.keySet()){
            Log.e("TAG", s+":"+bundle.get(s)+"\n");
        }
        list= (List<MedicineDetails>) bundle.getSerializable("medicineList");
        adapter=new MedicineAdapter(list, this);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }
}
