package com.android.onroad.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
import com.android.onroad.R;
import com.android.onroad.activities.DilogActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.reciever.MyReciver;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Utility {

    public static boolean doStringsMatch(String str1, String str2) {
        return str1.equals(str2);
    }

    public static void pushNotification(Context context, Trip trip) {

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context);
        Intent notificationIntent;
        PendingIntent contentIntent;
//        if (trip != null) {

        notificationIntent = new Intent(context, DilogActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationIntent.putExtra(Constants.TRIP, trip);
        final int _id = (int) System.currentTimeMillis();
        contentIntent = PendingIntent.getActivity(context, _id, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentText(trip.getTripName());
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(Notification.PRIORITY_MAX);

        Notification notification = builder.build();
        nm.notify((int) System.currentTimeMillis(), notification);
    }

    public static void setupAlarmManager(Context context, Trip trip, long timeInMillis,int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyReciver.class);
        intent.putExtra(Constants.TRIP, trip);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Toast.makeText(context, "calendar.getTimeInMillis :  "+SystemClock.elapsedRealtime()+timeInMillis , Toast.LENGTH_SHORT).show();
        Log.e("time"," " +SystemClock.elapsedRealtime()+timeInMillis);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis
                , AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void setAlarmTime(Context context, Trip trip, int hourOfDay, int minute,int day,int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.DAY_OF_YEAR,day);
        if (calendar.getTimeInMillis() > Calendar.getInstance()
                .getTimeInMillis()) {
            Utility.setupAlarmManager(context, trip,
                    calendar.getTimeInMillis(),id);

            Toast.makeText(context, "calendar.getTimeInMillis :  "+calendar.getTimeInMillis(), Toast.LENGTH_SHORT).show();
        } else {
//            calendar.add(Calendar.DAY_OF_YEAR, day);
            Utility.setupAlarmManager(context, trip,
                    calendar.getTimeInMillis(),id);
        }
    }


    public static void launchMap(Context context, Trip trip) {
//            Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=18.519513,73.868315&destination=18.518496,
// 73.879259&waypoints=18.520561,73.872435|18.519254,73.876614|18.52152,73.877327|18.52019,73.879935&travelmode=driving");
//            Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//            intent.setPackage("com.google.android.apps.maps");
//            try {
//                context.startActivity(intent);
//            } catch (ActivityNotFoundException ex) {
//                try {
//                    Intent unrestrictedIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    context.startActivity(unrestrictedIntent);
//                } catch (ActivityNotFoundException innerEx) {
//                    Toast.makeText(context, "Please install a maps application", Toast.LENGTH_LONG).show();
//                }
//            }
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps/dir?saddr=" + trip.getStartPoint() + "&daddr=" + trip.getEndPoint()));
        intent.setPackage("com.google.android.apps.maps");
        context.startActivity(intent);
    }
}
