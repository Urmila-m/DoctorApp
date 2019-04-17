package com.myapp.doctorapp.backgroundtasks;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.myapp.doctorapp.services.NotificationService;

import java.util.Calendar;

public class NotificationAlarmThread extends Thread {
    private Calendar appointDate;
    private AlarmManager alarmManager;
    private PendingIntent pi;
    private Context context;
    private String doctorName;
    private boolean threadRun;

    public boolean isThreadRun() {
        return threadRun;
    }

    public void setThreadRun(boolean threadRun) {
        this.threadRun = threadRun;
    }

    public NotificationAlarmThread(Context context, Calendar calendar, String doctorName){
        this.context=context;
        this.doctorName=doctorName;
        appointDate=calendar;
        alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        threadRun=true;
        Log.e("TAG", "NotificationAlarmThread: constructor" );
    }

    @Override
    public void run() {
        super.run();
        Log.e("TAG", "run: "+ appointDate.get(Calendar.MINUTE));
        while (threadRun){
//            Log.e("TAG", "run: ");
            Intent intent = new Intent(context, NotificationService.class);
            intent.putExtra("title", "Appointment");
            intent.putExtra("message", "You have an appoinment with "+doctorName+" today at "+appointDate.get(Calendar.HOUR_OF_DAY)+":"+appointDate.get(Calendar.MINUTE));
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, appointDate.getTimeInMillis(), pi);
//            if (appointDate.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
//                Log.e("TAG", "run: while bata return");
//                threadRun=false;
//                alarmManager.cancel(pi);
//                return;
//            }
        }

        if (!threadRun){
            alarmManager.cancel(pi);
            Log.e("TAG", "run: thread stopping" );
            return;

        }

    }
}
