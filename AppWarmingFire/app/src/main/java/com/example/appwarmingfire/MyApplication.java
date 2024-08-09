package com.example.appwarmingfire;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "MY_APPLICATION";

    private static MyApplication instance ;

   private boolean mFirstBoot = false ;
   public boolean getMFirstBoot() {
       return mFirstBoot;
   }

   public void setMFirstBoot(boolean data){
       this.mFirstBoot = data ;
   }
    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        createNotificationChannel();
        mFirstBoot = true ;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "APP_WARMING_FIRE", importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
