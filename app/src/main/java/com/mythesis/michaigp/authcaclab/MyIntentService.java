package com.mythesis.michaigp.authcaclab;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.commonsware.cwac.wakeful.WakefulIntentService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;


public class MyIntentService extends WakefulIntentService {

    private static String URL = "http://michaigp.webpages.auth.gr/project3/?q=en/node/22";

    public static final String MY_PREFS_NAME = "MyPrefs";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void doWakefulWork(Intent intent) {
        //Log.i("MyIntentService", "Service running");

        Document doc;
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (preferences.contains("lang")) {
            String lang = preferences.getString("lang", null);

            if(lang!=null && lang.equals("el")){
                URL = "http://michaigp.webpages.auth.gr/project3/?q=el/node/21";
            }
        }

        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            //Log.e("JsoupError", "~~~~Error Connecting to URL");
            e.printStackTrace();
            //Log.e("JsoupError","1"+e.getLocalizedMessage());
            return;
        }

        String modDate = doc.select("div.modDate").html();//Get the modification date from the html file
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        //if the lastUpdate variable exists in prefs
        if (modDate != null && getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).contains("lastUpdate")) {
            if(modDate.contains(getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).getString("lastUpdate",null))){
                //Log.i("JsoupError","No notification");
                return;
            }
            try {
                notification(getString(R.string.new_announcement),"DateTime: " + modDate);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                //Log.e("JsoupError", "1" + e.getLocalizedMessage());
            }
            editor.putString("lastUpdate",modDate);
            editor.apply();
        }else if (modDate != null && !getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).contains("lastUpdate")){//The variable doesn't exist in prefs
            //Log.i("JsoupError", "Second if, no prefs");
            try {
                notification(getString(R.string.new_announcement), "DateTime: " + modDate);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                //Log.e("JsoupError", "2" + e.getLocalizedMessage());
            }

            editor.putString("lastUpdate", modDate);
            editor.apply();
        }else{
            //Log.e("JsoupError","~~~~ModDate: is null");
            return;
        }
    }


    private void notification(String title,String content) throws URISyntaxException {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_announcement_white_24dp)
                        .setContentTitle(title)//Change to variable
                        .setContentText(content)//Change to variable
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis())
                        .setSound(sound);

        Intent resultIntent = new Intent(this, MainActivity.class);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        // Sets an ID for the notification
        int mNotificationId = 2183;

        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }
}
