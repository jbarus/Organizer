package com.example.organizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper=new NotificationHelper(context);
        NotificationCompat.Builder nb=notificationHelper.getChannel1Notification("Przypomnienie","Hej, zaplanowane przez ciebie wydarzenie rozpocznie się za dwie godziny.");
        notificationHelper.getManager().notify(1,nb.build());



    }
}
