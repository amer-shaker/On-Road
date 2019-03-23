package com.android.onroad.utils;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.onroad.FloatWidgetIntentService;
import com.android.onroad.R;
import com.android.onroad.activities.DilogActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.reciever.TripAlarmReceiver;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Utility {

    private static FirebaseDatabase mFirebaseDatabase;
    static FloatWidgetIntentService mService;
    static boolean mBound = false;

    public static FirebaseDatabase getFirebaseDatabaseInstance() {
        if (mFirebaseDatabase == null) {
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.setPersistenceEnabled(true);
        }
        return mFirebaseDatabase;
    }

    private static ServiceConnection myConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            FloatWidgetIntentService binder = (FloatWidgetIntentService) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
        }
    };

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
        Log.i("trip_namepushNotific", trip.getName());

        final int _id = (int) System.currentTimeMillis();
        contentIntent = PendingIntent.getActivity(context, _id, notificationIntent, 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        Log.i("trip_namepushNotific", trip.getName());

        builder.setContentText(trip.getName());
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setAutoCancel(true);
        builder.setOngoing(true);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setPriority(Notification.PRIORITY_MAX);

        Notification notification = builder.build();
        nm.notify((int) System.currentTimeMillis(), notification);
    }

    public static void setupAlarmManager(Context context, Trip trip, long timeInMillis, int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TripAlarmReceiver.class);
        Log.i("trip_name setupAlarm", trip.getName());
        intent.putExtra(Constants.TRIP, trip);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_IMMUTABLE);
        Log.e("time", " " + SystemClock.elapsedRealtime() + timeInMillis);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis
                , AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void setAlarmTime(Context context, Trip trip, int hourOfDay, int minute, int day, int id) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, day);
        Log.i("trip_name setAlarmTime", trip.getName());
        Log.i("Calendar.DATE", Calendar.DATE + "");


        if (calendar.getTimeInMillis() > Calendar.getInstance()
                .getTimeInMillis()) {
            Utility.setupAlarmManager(context, trip,
                    calendar.getTimeInMillis(), id);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Utility.setupAlarmManager(context, trip,
                    calendar.getTimeInMillis(), id);

        }
    }

    public static void launchMap(Context context, Trip trip) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,

                    Uri.parse("package:" + context.getPackageName()));
            ((AppCompatActivity) context).startActivityForResult(intent, 1);
        } else {
            Intent intent = new Intent(context, FloatWidgetIntentService.class);
            intent.putExtra(Constants.TRIP, trip);

            context.startService(intent);


            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps/dir?saddr=" + trip.getStartPoint() + "&daddr=" + trip.getEndPoint()));
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);


        }
    }

    public static void cancelAlarm(Context context, int alarmId) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent myIntent = new Intent(context, TripAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, alarmId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
