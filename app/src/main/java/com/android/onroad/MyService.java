package com.android.onroad;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.android.onroad.models.TripModel;
import com.android.onroad.utils.Utility;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TripModel trip = intent.getExtras().getParcelable("");
        Utility.pushNotification(getApplicationContext(),"");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


}
