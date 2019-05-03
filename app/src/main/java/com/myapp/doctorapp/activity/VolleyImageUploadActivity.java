package com.myapp.doctorapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.myapp.doctorapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class VolleyImageUploadActivity extends PreferenceInitializingActivity implements View.OnClickListener {

    ImageView imageView;
    TextInputEditText enterImageName;
    Button btnChoose, btnUpload;
    Bitmap imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_image_upload);

        imageView=findViewById(R.id.iv_upload_image);
        enterImageName=findViewById(R.id.et_upload_image);
        btnChoose=findViewById(R.id.btn_choose_image);
        btnUpload=findViewById(R.id.btn_upload_image);

        String image=preferences.getString("image", "");
        Picasso.get().load(image)
                .placeholder(R.drawable.default_image)
                .into(imageView);
        btnChoose.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==btnChoose){
            //TODO device bata photo choose garrna dine
            if (ActivityCompat.checkSelfPermission(VolleyImageUploadActivity.this,  Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(VolleyImageUploadActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (ActivityCompat.checkSelfPermission(VolleyImageUploadActivity.this,  Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                Intent chooseImageIntent=new Intent();
                chooseImageIntent.setType("image/*");
                chooseImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(chooseImageIntent, 2);
            }
            else {
                Toast.makeText(this, "Permission to read external storage not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v==btnUpload){

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode==RESULT_OK&& data!=null){
            //TODO get the image selected
            Uri path=data.getData();
            try {
                imageSelected= MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(imageSelected);

            enterImageName.setVisibility(View.VISIBLE);
        }
    }
}
