package com.android.onroad.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DilogActivity extends AppCompatActivity {

    Button btnStart, btnLater, btnCancel;
    TextView tvTripName;
    MediaPlayer myPlayer;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;

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

        tvTripName.setText(trip.getName());

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
                Log.i("trip_name dialoyge", trip.getName());

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

    private void updateTrip(Trip trip) {

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user != null) {
            String userId = mFirebaseAuth.getCurrentUser().getUid();

            mTripsDatabaseReference.child(userId)
                    .child("name")
                    .setValue(trip.getName());

            mTripsDatabaseReference.child(userId)
                    .child("date")
                    .setValue(trip.getDate());

            mTripsDatabaseReference.child(userId)
                    .child("startPoint")
                    .setValue(trip.getStartPoint());

            mTripsDatabaseReference.child(userId)
                    .child("endPoint")
                    .setValue(trip.getEndPoint());

            mTripsDatabaseReference.child(userId)
                    .child("startPointLatitude")
                    .setValue(trip.getStartPointLatitude());

            mTripsDatabaseReference.child(userId)
                    .child("startPointLongitude")
                    .setValue(trip.getStartPointLongitude());

            mTripsDatabaseReference.child(userId)
                    .child("endPointLatitude")
                    .setValue(trip.getEndPointLatitude());

            mTripsDatabaseReference.child(userId)
                    .child("endPointLongitude")
                    .setValue(trip.getEndPointLongitude());

            mTripsDatabaseReference.child(userId)
                    .child("type")
                    .setValue(trip.getType());

            mTripsDatabaseReference.child(userId)
                    .child("status")
                    .setValue(trip.getStatus());

            mTripsDatabaseReference.child(userId)
                    .child("notes")
                    .setValue(trip.getNotes());
        }
    }



}

