package com.android.onroad;

import android.app.Dialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Utility;

public class MyService extends Service {
    Button btnStart,btnLater,btnCancel;
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Trip trip = intent.getExtras().getParcelable("");
        Utility.pushNotification(getApplicationContext(),"");

        // adding dialog
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        btnStart=dialog.findViewById(R.id.btnStart);
        btnLater=dialog.findViewById(R.id.btnLater);
        btnCancel=dialog.findViewById(R.id.btnCancel);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adding intent to move to trip and start


            }
        });
     btnLater.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Utility.pushNotification(getApplicationContext(),"");

         }
     });

     btnCancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             // canceling trip
             // adding it to history


         }
     });



        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }


}
