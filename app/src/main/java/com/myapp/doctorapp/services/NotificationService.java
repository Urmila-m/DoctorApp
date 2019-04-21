package com.myapp.doctorapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.preference.Preference;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.activity.AfterLoginActivity;
import com.myapp.doctorapp.activity.MedicineActivity;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStartCommand: service started");
        String title=intent.getStringExtra("title");
        String msg=intent.getStringExtra("message");
        String doctorName = null;
        if(intent.getStringExtra("doctor")!=null){
            Log.e("TAG", "onStartCommand: doctor in notification not null" );
            doctorName=intent.getStringExtra("doctor");
        }
        Log.e("TAG", "onStartCommand: doctor"+doctorName );
        SharedPreferences preferences= getSharedPreferences("loggedIn", MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("notification_doctor", doctorName).commit();
        Log.e("TAG", "onStartCommand: " +preferences.getString("notification_doctor", ""));
        showNotification(title, msg, preferences.getString("notification_doctor", ""));

        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification(String title, String msg, String doctor) {
        Intent intent = null;
        if (title.equals("Appointment")) {
            intent = new Intent(this, AfterLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        else if (title.equals("Post Appointment Medicine Details")){
            intent = new Intent(this, MedicineActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("doctor", doctor);
        }
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "11");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = notificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("11", "service", importance);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        builder.setContentTitle(title)
                .setSmallIcon(R.drawable.ic_menu_slideshow)
                .setLargeIcon(bitmap1)
                .setContentIntent(pi)
                .setContentText(msg)
//                .setOngoing(true)     //doesnt allow user to clear the notification by swiping
                .setAutoCancel(true)    //clears the notification when clicked
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setDefaults(NotificationCompat.DEFAULT_SOUND);

        Notification notification=builder.build();
        if (title.equals("Post Appointment Medicine Details")){
            notification.flags|=Notification.FLAG_NO_CLEAR; //same as "builder.setOngoing(true)", doesnt let user clear the notification
        }
        notificationManager.notify((int) (Math.random() * 1000), notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
