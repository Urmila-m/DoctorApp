package com.myapp.doctorapp.backgroundtasks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyImageUploadTask {
    private Context context;
    private RequestQueue requestQueue;
    private static VolleyImageUploadTask instance;//static, so that even for multiple object instances, this member remains same for all

    private VolleyImageUploadTask(Context context) {//private constructor, this is a singleton class, so no other class/object should be able to instantiate it
        this.context=context;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public static synchronized VolleyImageUploadTask getInstance(Context context){
        if (instance==null){
            instance=new VolleyImageUploadTask(context);
        }
        return instance;
    }

    public<T> void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
