package com.myapp.doctorapp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;
import com.google.zxing.common.StringUtils;
import com.myapp.doctorapp.R;
import com.myapp.doctorapp.backgroundtasks.VolleyImageUploadTask;
import com.myapp.doctorapp.model.PostResponse;
import com.myapp.doctorapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Permissions;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.myapp.doctorapp.Globals.LOCAL_SERVER_IP;

public class VolleyImageUploadActivity extends PreferenceInitializingActivity implements View.OnClickListener {

    ImageView imageView;
    TextInputEditText enterImageName;
    TextInputLayout textInputLayout;
    Button btnChoose, btnUpload;
    Bitmap imageSelected;
    String image;
    String SERVER_URL="http://"+LOCAL_SERVER_IP+"/uploadImage.php";
    ProgressBar progressBar;
    NetworkUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_image_upload);

        imageView=findViewById(R.id.iv_upload_image);
        enterImageName=findViewById(R.id.et_upload_image);
        btnChoose=findViewById(R.id.btn_choose_image);
        btnUpload=findViewById(R.id.btn_upload_image);
        textInputLayout=findViewById(R.id.til_image_upload);
        progressBar=findViewById(R.id.pb_upload_image);

        utils=new NetworkUtils();

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
            if (ActivityCompat.checkSelfPermission(VolleyImageUploadActivity.this,  Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(VolleyImageUploadActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }

            if (ActivityCompat.checkSelfPermission(VolleyImageUploadActivity.this,  Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
            else {
                Toast.makeText(this, "Permission to read external storage not granted", Toast.LENGTH_SHORT).show();
            }
        }
        else if (v==btnUpload){
            if (utils.isNetworkConnected(this)) {
                if (textInputLayout.getError() == null) {
                    if (imageSelected != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        image = bitmapToString(imageSelected);
                        uploadImage();
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            Log.e("TAG", "onClick: permisison granted now write");
                            writeToExternalStorage(imageSelected, getRequiredEmail(preferences.getString("email", "")) + getImageName());
                        } else {
                            Log.e("TAG", "onClick: write permission not granted");
                        }
                    }
                }
            }
            else {
                Toast.makeText(this, "No internet connection!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2&&resultCode==RESULT_OK&& data!=null){
            Uri path=data.getData();
            try {
                Log.e("TAG", "onActivityResult: "+path);
                imageSelected= MediaStore.Images.Media.getBitmap(getContentResolver(), path);//converts the image selected into bitmap object
            } catch (IOException e) {
                e.printStackTrace();
            }
            textInputLayout.setVisibility(View.VISIBLE);
            btnUpload.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(imageSelected);

            enterImageName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().contains(" ")) {
                        textInputLayout.setError("no white spaces allowed");
                    }
                    else if (!Pattern.matches("[a-zA-Z_0-9]+", s.toString())){ //patterns and regex
                        textInputLayout.setError("special chars except '_' cannot be used!!");
                    }
                    else {
                        textInputLayout.setError(null);
                    }
                }
            });
        }
    }

    void selectImage(){
        Intent chooseImageIntent=new Intent();
        chooseImageIntent.setType("image/*");
        chooseImageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseImageIntent, 2);
    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] imgBytes=outputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void uploadImage(){
        StringRequest request=new StringRequest(Request.Method.POST, SERVER_URL,//StringRequest class from Volley
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("TAG", "onResponse: "+response);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(VolleyImageUploadActivity.this, "uploaded successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(VolleyImageUploadActivity.this, AfterLoginActivity.class));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "onErrorResponse: "+error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> stringMap=new HashMap<> ();
                String email=preferences.getString("email", "");
                stringMap.put("image", image);
                stringMap.put("email", email);
                stringMap.put("action", "uploadImage");
                stringMap.put("today", getDate());
                stringMap.put("imageName", getImageName());
                stringMap.put("requiredEmail", getRequiredEmail(email));
                return stringMap;

            }
        };

        VolleyImageUploadTask task=VolleyImageUploadTask.getInstance(this);
        task.addToRequestQueue(request);
    }

    private void writeToExternalStorage(Bitmap bitmap, String title){
        File file=new File(Environment.getExternalStorageDirectory().getPath(), "DoctorApp");
        if (!file.exists()){
            Log.e("TAG", "writeToExternalStorage: file doesnt exist" );
            file.mkdir();
        }
        if (file.exists()) {
            Log.e("TAG", "writeToExternalStorage: imageInsertion");
            File exactImageLocation=new File(file, title+".png");
            try {
                OutputStream outputStream=new FileOutputStream(exactImageLocation);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private String getRequiredEmail(String email){
        String emailRequired=email.split("@")[0];//string before '@' from email
        //emailRequired=email.substring(0, email.indexOf('@'));
        return emailRequired;
    }
    private String getImageName(){
        Calendar c=Calendar.getInstance();
        long msec=c.getTimeInMillis();
        String strMsec=msec+"";
//        String last4milsec=strMsec.substring(strMsec.length()-4);//filters the last 4 digits of the current time in milliseconds
        return strMsec;
    }

    private String getDate(){
        Calendar c=Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DAY_OF_MONTH);
        String today= String.format("%02d", month)+"/"+String.format("%02d", day)+"/"+String.format("%04d", year);
        return  today;
    }
}
