package com.android.onroad.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.android.onroad.R;
import com.android.onroad.activities.HomeActivity;
import com.android.onroad.activities.LoginActivity;
import com.android.onroad.beans.Trip;

import java.util.Calendar;

import reciever.MyReciver;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Utility {
    
    public static boolean doStringsMatch(String str1, String str2) {
        return str1.equals(str2);
    }
    public static void pushNotification(String txt, Trip  trip) {

        NotificationManager nm = (NotificationManager) App.context.getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(App.context);
        Intent notificationIntent;
        PendingIntent contentIntent;
        if (trip != null) {
            notificationIntent = new Intent(App.context, LoginActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            notificationIntent.putExtra(Constants.TRIP, trip);
            contentIntent = PendingIntent.getActivity(App.context, trip.getTripId(), notificationIntent, 0);
            builder.setContentIntent(contentIntent);

        } else {
            notificationIntent = new Intent(App.context, HomeActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            contentIntent = PendingIntent.getActivity(App.context, 0, notificationIntent, 0);
            builder.setContentIntent(contentIntent);
        }

            builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText(txt);
        builder.setContentTitle(App.context.getString(R.string.app_name));
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_ALL);

        Notification notification = builder.build();
        nm.notify((int) System.currentTimeMillis(), notification);
    }
    public static void setupAlarmManager(Class MyReciver, long timeInMillis) {
        AlarmManager alarmMgr = (AlarmManager) App.context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(App.context, MyReciver);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(App.context, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis
                , AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    public static void setAlarmTime( int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() > Calendar.getInstance()
                .getTimeInMillis()) {
            Utility.setupAlarmManager(MyReciver.class,
                    calendar.getTimeInMillis());
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Utility.setupAlarmManager(MyReciver.class,
                    calendar.getTimeInMillis());
        }
    }
}
