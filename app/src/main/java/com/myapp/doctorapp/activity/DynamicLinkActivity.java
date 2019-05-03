package com.myapp.doctorapp.activity;//not in use

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.myapp.doctorapp.R;

public class DynamicLinkActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_link);
        textView=findViewById(R.id.tv_dynamic_link);
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        if (pendingDynamicLinkData!=null){
                            Uri deeplink=pendingDynamicLinkData.getLink();
                            Log.e("TAG", "onSuccess: "+"onSuccess");
                            textView.setText(deeplink.toString());
                        }
                    }
                });

    }
}
