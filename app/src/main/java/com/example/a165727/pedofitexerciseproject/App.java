package com.example.a165727.pedofitexerciseproject;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    public static final String  dailyNotification = "dailyNotification";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID, "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT

            );

            NotificationChannel dailyNotificationChannel = new NotificationChannel(
                    dailyNotification, "PEDOFIT",
                    NotificationManager.IMPORTANCE_DEFAULT

            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
            manager.createNotificationChannel(dailyNotificationChannel);
        }
    }
}

