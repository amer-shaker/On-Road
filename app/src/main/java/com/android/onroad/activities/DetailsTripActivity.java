package com.android.onroad.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;

public class DetailsTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);
        Trip trip=getIntent().getParcelableExtra("trip");
        if (trip!=null)
        Toast.makeText(this, ""+trip.getTripName(), Toast.LENGTH_SHORT).show();
    }
}
