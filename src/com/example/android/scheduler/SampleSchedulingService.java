package com.example.android.scheduler;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService() {
        super("SchedulingService");
    }
    
    public static final String TAG = "Scheduling Demo";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private int numMessagesOne = 0;
    
    @Override
    protected void onHandleIntent(Intent intent) {
    	String message = "Voila une petite notification " +
        		"1. JPA Mini Book " +
        		"2. JVM Troubleshooting Guide " +
        		"3. JUnit Tutorial for Unit Testing " +
        		"4. Java Annotations Tutorial " +
        		"5. Java Interview Questions " +
        		"6. Spring Interview Questions " +
        		"7. Android UI Design ";
    	
        sendNotification(message);
        
        SampleAlarmReceiver.completeWakefulIntent(intent);
    }
    
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
               this.getSystemService(Context.NOTIFICATION_SERVICE);
    
        Intent pIntent = new Intent(this, MainActivity.class);
        pIntent.putExtra("text", msg);
        
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
        		pIntent , 0);
        
        
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_launcher)
        .setContentTitle(getString(R.string.doodle_alert))
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(msg))
        .setAutoCancel(true)
        .addAction(R.drawable.abc_ic_voice_search_api_mtrl_alpha, "Call", contentIntent)
        .addAction(R.drawable.abc_ic_star_black_16dp, "More", contentIntent)
        .addAction(R.drawable.abc_ratingbar_indicator_material, "And more", contentIntent)
        .setContentText(msg);
        
        mBuilder.setNumber(++numMessagesOne);
        
        mBuilder.setVibrate(new long[] {1000,500,1000});
        mBuilder.setSound( RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
		
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify((int)(Math.random() * 100 ), mBuilder.build());
    }

}
