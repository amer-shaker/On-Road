package com.android.onroad.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsTripActivity extends AppCompatActivity {
    @BindView(R.id.tv_trip_name_details)
    TextView tvTripame;
    @BindView(R.id.tv_start_date_details)
    TextView tvDate;
    @BindView(R.id.tv_start_point_details)
    TextView tvStartPoint;
    @BindView(R.id.tv_end_point_details)
    TextView tvEndPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);
        ButterKnife.bind(this);
        Trip trip = getIntent().getParcelableExtra("trip");
        if (trip != null) {
            tvTripame.setText(trip.getTripName());
            tvDate.setText(trip.getDateTime()+"");
            tvStartPoint.setText(trip.getStartPoint());
            tvEndPoint.setText(trip.getEndPoint());

        }
    }
}
