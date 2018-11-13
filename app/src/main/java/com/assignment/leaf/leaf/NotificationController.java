package com.assignment.leaf.leaf;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public  class NotificationController {
    public static final int NOTIFICATION_ID=10001;

    public static void showNotification(Context context,String location){
        Notification notification;
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT>=26) {
            NotificationCompat.Builder mBuilder;
            NotificationManager mNotificationManager;
            Intent resultIntent = new Intent(context, MainActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder = new NotificationCompat.Builder(context);
            mBuilder.setSmallIcon(R.drawable.loc);
            mBuilder.setContentTitle("New Location").setContentText(location).setAutoCancel(false).setOngoing(true).setContentIntent(resultPendingIntent);
            mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel=new NotificationChannel(String.valueOf(NOTIFICATION_ID),"New Location",importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.enableVibration(false);
            mBuilder.setChannelId(String.valueOf(NOTIFICATION_ID));
            mNotificationManager.createNotificationChannel(notificationChannel);
            notification=mBuilder.build();
            notification.flags|= notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(0,notification);



        }

        else{
            Notification.Builder builder=new Notification.Builder(context);
            builder.setContentText(location).setContentTitle("New Location");
            builder.setSmallIcon(R.drawable.loc);

            Intent resultIntent=new Intent(context,MainActivity.class);

            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,resultIntent,0);
            builder.setContentIntent(pendingIntent);
            builder.setAutoCancel(true);
            builder.setOngoing(false);
            notification=builder.build();
            notification.flags|= notification.FLAG_AUTO_CANCEL;
            if(notificationManager!=null){
                notificationManager.notify(NOTIFICATION_ID,notification);
            }

        }

    }

}
