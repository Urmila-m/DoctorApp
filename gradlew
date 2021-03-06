package com.example.serviceexample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AsyncActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn, btnInsert, btnRead;
    EditText editText;
    TextView textView;
    CustomAsyncAndroid asyncAndroid;
    Handler handler;
    String[] permission;
    Uri uri;
    int prime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);
        editText=findViewById(R.id.et_async);
        textView=findViewById(R.id.tv_prime);
        btn=findViewById(R.id.btn_async);
        btnInsert=findViewById(R.id.btn_another_database);
        btnRead=findViewById(R.id.btn_read_database);
        btn.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        permission=new String[]{"com.example.thirdapplication.ExampleContentProvider.permission.DB_WRITE"};
        uri=Uri.parse("content://com.example.thirdapplication.ExampleContentProvider/database");
        ActivityCompat.requestPermissions(this, permission, 121);

    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onClick(View v) {
        if(v==btn) {
            String strPrime = editText.getText().toString();
            prime = Integer.parseInt(strPrime);
            if (prime > 0) {
                handler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        List<Integer> getInteger = msg.getData().getIntegerArrayList("prime list");
                        for (Integer i : getInteger
                        ) {
                            textView.setText(String.format("%s\n%s", textView.getText(), Integer.toString(i)));
                        }
                    }
                };
                asyncAndroid = new CustomAsyncAndroid(this, handler, prime);
//            asyncAndroid.setOnPrimeSelectListener(this);
                asyncAndroid.execute();
            }
        }

        else if(v==btnInsert){
            if (ContextCompat.checkSelfPermission(this, "com.example.thirdapplication.ExampleContentProvider.permission.DB_WRITE")!= PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                Toast.makeText(this, "Permission noty granted!!", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues cv = new ContentValues();
                cv.put("Name", "Urmila");
                cv.put("Password", "Maharjan");
                cv.put("Birthdate", "2017 02 19");
                cv.put("Choice", "Noodles");
                cv.put("SamosaTicked", "Yes");
                cv.put("KhajaTicked", "Yes");
                cv.put("FoodType", "Good");
                cv.put("Age", "1");

                getContentResolver().insert(uri, cv);
            }

        }

        else if(v==btnRead){
            Cursor c=getContentResolver().query(uri,null, null, null, null );
            if (c.moveToFirst()) {
                    int id=c.getInt(0);//getting id
                    String name=c.getString(1)
                            , password=c.getString(2)
                            , choice=c.getString(4)
                            , samosa=c.getString(5)
                            , khaja=c.getString(6)
                            , birthdate=c.getString(3)
                            , foodtype=c.getString(7);

                Toast.makeText(this, name+" "+password+" "+choice+" "+samosa+" "+khaja+" "+birthdate+" "+foodtype, Toast.LENGTH_SHORT).show();
                }
            }
        }


//    @Override
//    public void onSuccess(String s) {
//        textView.setText(s);
//    }
//
//    @Override
//    public void onProgressUpdate(int i) {
//        textView.setText("Progress " + i);
//    }


//    @Override
//    public void onSelected(final List<Integer> ints) {
//        for (Integer i : ints){
//            textView.setText(String.format("%s\n%s", textView.getText(), Integer.toString(i)));
//        }
//
//
//    }
}
                                                                                                                                                                                                                                                                                                                                              