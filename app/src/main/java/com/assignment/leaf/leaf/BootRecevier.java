package com.assignment.leaf.leaf;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class BootRecevier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent!=null && intent.getAction()!=null){
            Intent myService = new Intent(context, MyLocationService.class);
            if(Build.VERSION.SDK_INT>=26){
               context.startForegroundService(myService);
            }
            else
            context.startService(myService);
        }

    }
}
