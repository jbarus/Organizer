package com.example.organizer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1ID";
    public static final String chanel1Name = "Chanel1 Name";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        createChanel();
    }

    public void createChanel() {
        NotificationChannel channel = new NotificationChannel(channel1ID, chanel1Name, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        getManager().createNotificationChannel(channel);

    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannel1Notification(String title, String message)
    {
        return new NotificationCompat.Builder(getApplicationContext(),channel1ID)
                .setContentTitle(title)
                .setContentText(message).setSmallIcon(R.drawable.ic_notification);
    }
}
