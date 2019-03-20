package com.android.onroad.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.onroad.activities.DilogActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Trip trip = intent.getExtras().getParcelable(Constants.TRIP);
            Intent intentDialogue = new Intent(context, DilogActivity.class);
            intentDialogue.putExtra(Constants.TRIP, trip);
            intentDialogue.putExtra(Constants.FIRE_SOUND_STATUS, "fired");
            intentDialogue.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentDialogue);
        }
    }
}
