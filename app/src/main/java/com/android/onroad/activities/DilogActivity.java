package com.android.onroad.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;

public class DilogActivity extends AppCompatActivity {
    Button btnStart, btnLater, btnCancel;
    TextView tvTripName;
    MediaPlayer myPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dilog);
        final Trip trip = getIntent().getExtras().getParcelable(Constants.TRIP);
        btnStart = findViewById(R.id.btnStart);
        btnLater = findViewById(R.id.btnLater);
        btnCancel = findViewById(R.id.btnCancel);
        tvTripName = findViewById(R.id.tripNameInDialog);
        String status = getIntent().getStringExtra(Constants.FIRE_SOUND_STATUS);
//        tvTripName.setText(trip.getName());

        if (status != null) {
            myPlayer = MediaPlayer.create(DilogActivity.this, R.raw.alarm_dialog);
            myPlayer.start();
            myPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    myPlayer.start();
                }
            });
        }


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adding intent to move to trip and start


                Utility.launchMap(DilogActivity.this, trip);
                if (myPlayer != null) {
                    myPlayer.stop();
                }
                finish();
            }
        });
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.pushNotification(DilogActivity.this, trip);
                if (myPlayer != null) {
                    myPlayer.stop();
                }
                finish();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myPlayer != null) {
                    myPlayer.stop();
                }
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {


    }
}

