package com.android.onroad.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.onroad.MyService;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;

public class MyReciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Trip  trip=intent.getExtras().getParcelable(Constants.TRIP);
        context.startService(new Intent(context, MyService.class).
                putExtra(Constants.TRIP, trip));
    }
}
