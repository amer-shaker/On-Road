package com.android.onroad.activities;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;

public class DilogActivity extends AppCompatActivity {
    Button btnStart, btnLater, btnCancel;
    TextView tvTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilog);
        Intent myIntent = getIntent();
        final Trip trip = myIntent.getExtras().getParcelable(Constants.TRIP);
        btnStart = findViewById(R.id.btnStart);
        btnLater = findViewById(R.id.btnLater);
        btnCancel = findViewById(R.id.btnCancel);
        tvTripName = findViewById(R.id.tripNameInDialog);
        tvTripName.setText(trip.getName());

    final MediaPlayer myPlayer=MediaPlayer.create(DilogActivity.this,R.raw.alarm_dialog);
    myPlayer.start();
    myPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            myPlayer.start();
        }
    });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adding intent to move to trip and start

                Utility.launchMap(DilogActivity.this,trip);
                myPlayer.stop();
                finish();
            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.pushNotification(DilogActivity.this, trip);
                myPlayer.stop();
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlayer.stop();
                finish();


            }
        });

    }

    @Override
    public void onBackPressed() {



    }
}

