package com.asa.taskscheduler;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by Phant on 1/8/2018.
 */

public class AlarmReceiver extends BroadcastReceiver{

    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    int notificationID = 1;
    public static long duration;
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getAction();
        duration = intent.getLongExtra("duration",0);
        Notify(context,"Task",name,duration);
        Log.d("tag","received");
    }

    public void Notify( Context context, String title, String text, final long duration){
         mBuilder = new NotificationCompat.Builder(context,"01");
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher));
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(text).setDefaults(Notification.DEFAULT_ALL) ;

         mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//        new Thread(
//                new Runnable() {
//                    public static final String TAG = "";
//                    int x = Integer.parseInt(duration + "");
//
//                    @Override
//                    public void run() {
//                        int incr;
//
//
//
//                        for (incr = 0; incr < duration; incr+=3) {
//                            // Sets the progress indicator to a max value, the
//                            // current completion percentage, and "determinate"
//                            // state
//                            mBuilder.setProgress(x,incr,false);
//                            // Displays the progress bar for the first time.
//                            mNotificationManager.notify(notificationID, mBuilder.build());
//                            // Sleeps the thread, simulating an operation
//                            // that takes time
//
//                        }
//                        /*try {
//                            // Sleep for 5 seconds
//                            Thread.sleep(5*1000);
//                        } catch (InterruptedException e) {
//                            Log.d(TAG, "sleep failure");
//                        }*/
//                   } }).start();



        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        //mBuilder.addAction(R.drawable.rate_it,"rate",resultPendingIntent);


        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());

    }


}


