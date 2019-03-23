package com.android.onroad.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.onroad.activities.DilogActivity;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;

public class TripAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Trip trip = intent.getExtras().getParcelable(Constants.TRIP);
        Intent intentDialogue = new Intent(context, DilogActivity.class);
        if (trip!=null){
            intentDialogue.putExtra(Constants.TRIP, trip);
            intentDialogue.putExtra(Constants.FIRE_SOUND_STATUS, "fired");
            intentDialogue.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentDialogue);
        }
        else {

        }

    }
}
