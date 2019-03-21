package com.android.onroad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Note;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;

import java.util.ArrayList;
import java.util.Date;

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
    @BindView(R.id.trip_type)
    TextView type;
@BindView( R.id.done_checkbox)
TextView doneCheckBox;
    @BindView(R.id.edit_trip_details)

    TextView editTripDetailsBtn;
    @BindView(R.id.start_btn_details)
    TextView startTripDetailsBtn;
    @BindView(R.id.noteList)
    ListView noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_trip);
        ButterKnife.bind(this);
        final Trip trip = getIntent().getParcelableExtra(Constants.TRIP_EXTRA);

       // Toast.makeText(this, ""+trip.getName(), Toast.LENGTH_SHORT).show();
        if (trip != null) {
            tvTripame.setText(trip.getName());

            Date mydt= new Date(trip.getTime());
            String date=mydt.getDay()+"-" +(mydt.getMonth()+1 )+"-"+(mydt.getYear()+1900);

            tvDate.setText(date);
            tvStartPoint.setText(trip.getStartPoint());
            tvEndPoint.setText(trip.getEndPoint());
            startTimeTxt.setText(mydt.getHours() + ":"+mydt.getMinutes());
            if(trip.getStatus().equals("done")) {
                doneCheckBox.setSelected(true);
            doneCheckBox.setVisibility(View.VISIBLE);

            }
            mapStatusTxt.setText(trip.getStatus());
            type.setText(trip.getType());
            Toast.makeText(this, ""+trip.getNotes().get(0), Toast.LENGTH_SHORT).show();
                  trip.setDate(new Date(trip.getTime()));


            // Toast.makeText(this, arrayOfNotes.get(0).getNote()+" size of notes ", Toast.LENGTH_SHORT).show();

            ArrayList<String> myNotes = new ArrayList<>();

           for (int i = 0; i < trip.getNotes().size(); i++) {
               myNotes.add(trip.getNotes().get(i).getNote());
           }

            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myNotes);
            noteList.setAdapter(adapter);

            startTripDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

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