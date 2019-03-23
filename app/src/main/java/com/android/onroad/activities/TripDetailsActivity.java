package com.android.onroad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.FloatWidgetIntentService;
import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity {
    @BindView(R.id.tv_trip_name_details)
    TextView tvTripame;
    @BindView(R.id.tv_start_date_details)
    TextView tvDate;
    @BindView(R.id.tv_start_point_details)
    TextView tvStartPoint;
    @BindView(R.id.tv_end_point_details)
    TextView tvEndPoint;
    @BindView(R.id.start_time_detail)
    TextView startTimeTxt;
    @BindView(R.id.map_status)
    TextView mapStatusTxt;

    @BindView(R.id.done_checkbox)
    TextView doneCheckBox;
    @BindView(R.id.edit_trip_details)

    TextView editTripDetailsBtn;
    @BindView(R.id.start_btn_details)
    TextView startTripDetailsBtn;
    @BindView(R.id.noteList)
    ListView noteList;
    @BindView(R.id.notesLayout)
    LinearLayout notesLayout;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);
        ButterKnife.bind(this);


        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node));
        final Trip trip = getIntent().getParcelableExtra(Constants.TRIP_EXTRA);

        // Toast.makeText(this, ""+trip.getName(), Toast.LENGTH_SHORT).show();
        if (trip != null) {
            tvTripame.setText(trip.getName());

            mapStatusTxt.setText(trip.getStatus());

            tvDate.setText(trip.getTripDate());
            tvStartPoint.setText(trip.getStartPoint());
            tvEndPoint.setText(trip.getEndPoint());
            startTimeTxt.setText(String.valueOf(trip.getTime()));
            if (trip.getStatus().equals("done")) {
                doneCheckBox.setSelected(true);
                doneCheckBox.setVisibility(View.VISIBLE);

            }
            mapStatusTxt.setText(trip.getStatus());


            if (trip.getNotes() != null) {
                notesLayout.setVisibility(View.VISIBLE);

                ArrayList<String> myNotes = new ArrayList<>();

                for (int i = 0; i < trip.getNotes().size(); i++) {
                    myNotes.add(trip.getNotes().get(i).getNote());
                }

                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myNotes);
                noteList.setAdapter(adapter);
            }
            if (trip.getStatus().equals(Trip.CANCELED_TRIP) || trip.getStatus().equals(Trip.PAST_TRIP)) {
                startTripDetailsBtn.setVisibility(View.GONE);
                editTripDetailsBtn.setVisibility(View.GONE);
            } else {
                        startTripDetailsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            trip.setStatus(Trip.PAST_TRIP);
                            updateTrip(trip);
                            Utility.launchMap(TripDetailsActivity.this, trip);



                    }
                });

                editTripDetailsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent editIntent = new Intent(TripDetailsActivity.this, AddTripActivity.class);
                        editIntent.putExtra("trip", trip);
                        startActivity(editIntent);
                    }
                });
            }
        }
        }
    private void updateTrip(@NonNull Trip trip) {

        FirebaseUser user = mFirebaseAuth.getCurrentUser();

        if (user != null) {
            String userId = user.getUid();

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("name")
                    .setValue(trip.getName());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("date")
                    .setValue(trip.getDate());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("startPoint")
                    .setValue(trip.getStartPoint());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("endPoint")
                    .setValue(trip.getEndPoint());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("startPointLatitude")
                    .setValue(trip.getStartPointLatitude());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("startPointLongitude")
                    .setValue(trip.getStartPointLongitude());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("endPointLatitude")
                    .setValue(trip.getEndPointLatitude());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("endPointLongitude")
                    .setValue(trip.getEndPointLongitude());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("type")
                    .setValue(trip.getType());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("status")
                    .setValue(trip.getStatus());

            mTripsDatabaseReference.child(userId)
                    .child(trip.getTripId())
                    .child("notes")
                    .setValue(trip.getNotes());
        }
    }







}

