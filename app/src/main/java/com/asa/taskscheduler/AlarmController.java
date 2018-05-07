package com.asa.taskscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Phant on 1/8/2018.
 */

public class AlarmController {
        public static void addAlarm(Context context, int code, long time, String name,long duration){
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context,AlarmReceiver.class);
            intent.setAction(name);
            intent.putExtra("duration",duration);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,code,intent,0);

            alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);

        }
    public static void deleteAlarm(Context context, int code){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,code,intent,0);

        alarmManager.cancel(pendingIntent);

    }
}
