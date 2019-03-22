package com.android.onroad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;

public class MyService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Trip trip = intent.getExtras().getParcelable(Constants.TRIP);
        if (trip != null) {
            intent.putExtra(Constants.TRIP, trip);
            intent.putExtra(Constants.FIRE_SOUND_STATUS, "fired");
            Utility.pushNotification(getApplicationContext(), trip);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

}
