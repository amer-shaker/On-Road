package com.android.onroad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class AddTripActivity extends AppCompatActivity {

    private static final String TAG = "Error...";
    Button btnTimePicker, btnDatePicker, AddTrip;
    TextView txtDate,txtTime;
    Spinner spnRepeat , spnStatus;
    String Repeat,Status;
    EditText tripName,myNote;

    String tripNm, sPoint="", sLat="", sLong="", ePoint="", eLat="", eLong="";
    double mysLat, mysLong, myeLat, myeLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        btnDatePicker = findViewById(R.id.btnAddDate);
        btnTimePicker = findViewById(R.id.btnAddTime);
        txtDate = findViewById(R.id.in_date_add);
        txtTime = findViewById(R.id.in_time_add);

        tripName= findViewById(R.id.txtTripName);
        AddTrip = findViewById(R.id.btnAddTrip);
        spnRepeat = findViewById(R.id.spnRepeat);
        spnStatus = findViewById(R.id.spnStatus);
        myNote = findViewById(R.id.txtAddNote);

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Status =spnStatus.getItemAtPosition(position).toString();
                Toast.makeText(AddTripActivity.this, Status, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Repeat =spnRepeat.getItemAtPosition(position).toString();
                Toast.makeText(AddTripActivity.this, Repeat, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                        selectedmonth = selectedmonth + 1;
                        txtDate.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtStartPoint);
        if (autocompleteFragment1 != null)
            autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName());
                    sPoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    mysLat = myLatLong.latitude;
                    mysLong = myLatLong.longitude;
                    sLat = mysLat + "";
                    sLong = mysLong + "";


                }

                @Override
                public void onError(com.google.android.gms.common.api.Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();


        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtEndPoint);
        if (autocompleteFragment2 != null )
            autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place./
                    Log.i(TAG, "Place: " + place.getName());
                    String placeName = place.getName().toString();
                    Toast.makeText(AddTripActivity.this, "the place is "+ placeName ,Toast.LENGTH_SHORT).show();
                    ePoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    myeLat = myLatLong.latitude;
                    myeLong = myLatLong.longitude;
                    eLat = myeLat + "";
                    eLong = myeLong + "";
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();
    }
}
