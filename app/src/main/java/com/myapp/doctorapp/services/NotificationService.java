package com.myapp.doctorapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.myapp.doctorapp.R;
import com.myapp.doctorapp.activity.AfterLoginActivity;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("TAG", "onStartCommand: service started");
        String title=intent.getStringExtra("title");
        String msg=intent.getStringExtra("message");
        showNotification(title, msg);

        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification(String title, String msg) {

        Intent intent = new Intent(this, AfterLoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setDefaults(NotificationCompat.DEFAULT_SOUND);

        notificationManager.notify((int) (Math.random()*1000), builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
