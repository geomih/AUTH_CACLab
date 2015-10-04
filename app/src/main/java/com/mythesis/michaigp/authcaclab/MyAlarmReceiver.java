package com.mythesis.michaigp.authcaclab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;

public class MyAlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    public MyAlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyAlarmReceiver", "MyAlarmReceiver running");

        WakefulIntentService.sendWakefulWork(context,MyIntentService.class);

    }
}
