package com.android.onroad.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Note;
import com.android.onroad.beans.Trip;
import com.google.android.gms.common.api.Status;


import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {
    private static final String TAG = "Error...";
    Button btnTimePicker, btnDatePicker, btnAddTrip;
    TextView txtDate, txtTime;
    Spinner spnRepeat, spnStatus;

    EditText tripName, myNote;
    ListView lstMyNotes;

    Date date;
    Date myDateCheck;

    String  myStartPoint  = "" , myEndPoint = "";
    double mysLat, mysLong, myeLat, myeLong;
    ArrayList<Note> myArrayNote = new ArrayList<>();

    Date myDate = new Date();
    String myDay,myYear,myMonths,myMinutes,myHours;


    String myStatus,myRepeat;



     String myTripName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);




        lstMyNotes=(ListView)findViewById(R.id.lstTrips);
        btnDatePicker = findViewById(R.id.btnAddDate);
        btnTimePicker = findViewById(R.id.btnAddTime);
        txtDate = findViewById(R.id.in_date_add);
        txtTime = findViewById(R.id.in_time_add);

        tripName = findViewById(R.id.txtTripName);
        btnAddTrip = findViewById(R.id.btnAddTrip);
        spnRepeat = findViewById(R.id.spnRepeat);
        spnStatus = findViewById(R.id.spnStatus);
        myNote = findViewById(R.id.txtAddNote);
        PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtStartPoint);
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtEndPoint);

        Trip editObj = getIntent().getParcelableExtra("myTrip");
        if(editObj==null)
        {
            putDataInFields();
            btnAddTrip.setText("Update Trip");
            tripName.setText(editObj.getTripName());
            autocompleteFragment1.setText(editObj.getStartPoint());
            autocompleteFragment2.setText(editObj.getEndPoint());
            myDate = editObj.getDateTime();

            String editStatus = "Round Trip";//editObj.getStatus(); //the value you want the position for
            ArrayAdapter myAdap;
            myAdap= (ArrayAdapter) spnStatus.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition ;
            spinnerPosition= myAdap.getPosition(editStatus);
            spnStatus.setSelection(spinnerPosition);

            String editRepeat = editObj.getRepeat(); //the value you want the position for
             myAdap = (ArrayAdapter) spnRepeat.getAdapter(); //cast to an ArrayAdapter
             spinnerPosition = myAdap.getPosition(editRepeat);
            spnStatus.setSelection(spinnerPosition);

            txtDate.setText(myDate.getDay() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getYear());
            txtTime.setText(myDate.getHours() + ":" + myDate.getMinutes());







        }
        if (autocompleteFragment1 != null)
            autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName());
                    myStartPoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    mysLat = myLatLong.latitude;
                    mysLong = myLatLong.longitude;




                }

                @Override
                public void onError(com.google.android.gms.common.api.Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();



        if (autocompleteFragment2 != null )
            autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place./
                    Log.i(TAG, "Place: " + place.getName());
                    String placeName = place.getName().toString();
                    Toast.makeText(AddTripActivity.this, "the place is "+ placeName ,Toast.LENGTH_SHORT).show();
                    myEndPoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    myeLat = myLatLong.latitude;
                    myeLong = myLatLong.longitude;

                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();


        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(tripName.getText().toString().equals(""))

                    Toast.makeText(AddTripActivity.this, "enter the trip name", Toast.LENGTH_SHORT).show();

                if(myStartPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Start Point", Toast.LENGTH_SHORT).show();

                if(myEndPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter end Point", Toast.LENGTH_SHORT).show();

                if(txtDate.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Date", Toast.LENGTH_SHORT).show();

                if(txtTime.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Time", Toast.LENGTH_SHORT).show();
                else {
                    Trip myTrip = new Trip();

                    myTripName = tripName.getText().toString();
                    myTrip.setTripName(myTripName);
                    //myTrip.setDateTime(myDate);
                    myTrip.setYear(myYear);
                    myTrip.setMonth(myMonths);
                    myTrip.setDay(myDay);
                    myTrip.setHour(myHours);
                    myTrip.setMinute(myMinutes);
                    myTrip.setEndPoint(myEndPoint);
                    myTrip.setStartPoint(myStartPoint);
                    myTrip.setLatEndPoint(myeLat);
                    myTrip.setLangEndPoint(myeLong);
                    myTrip.setLatStartPoint(mysLat);
                    myTrip.setLangStartPoint(mysLong);
                    myTrip.setNotes(myArrayNote);
                    myTrip.setRepeat(myRepeat);
                    myTrip.setStatus(myStatus);
                }

//                Intent myIntent = new Intent(AddTripActivity.this,HomeActivity.class);
//                myIntent.putExtra("myTrip",myTrip);
//                startActivity(myIntent);
            }
        });

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myStatus = spnStatus.getItemAtPosition(position).toString();
                Toast.makeText(AddTripActivity.this, myStatus, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myRepeat = spnRepeat.getItemAtPosition(position).toString();
                Toast.makeText(AddTripActivity.this, myRepeat, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText(selectedHour + ":" + selectedMinute);

                        myHours=selectedHour+"";
                        myMinutes=selectedMinute+"";

                        myDate.setHours(selectedHour);
                        myDate.setMinutes(selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
              int  mYear = c.get(Calendar.YEAR);
               int mMonth = c.get(Calendar.MONTH);
             int   mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String mySDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                String timeStamp = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
                                DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                try {
                                    date = format.parse(timeStamp);
                                    myDateCheck = format.parse(mySDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if(myDateCheck.equals(null)) {
                                    try {
                                        myDateCheck = format.parse(timeStamp);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    if (myDateCheck.before(date)) {
                                        Toast.makeText(view.getContext(), "Enter Valid Date", Toast.LENGTH_SHORT).show();
                                    } else {
                                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                        myDate.setMonth(monthOfYear+1);
                                        myDate.setYear(year);
                                        myDate.setDate(dayOfMonth);
                                        myMonths=(monthOfYear+1)+"";
                                        myDay = dayOfMonth+"";
                                        myYear = year+"";

                                    }
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void putDataInFields() {
    }




    public void addNote(View view) {
        if(myNote.getText().toString().equals(""))
            Toast.makeText(this, "enter Note", Toast.LENGTH_SHORT).show();
        else{
            Note n = new Note();
            n.setNote(myNote.getText().toString());
            myArrayNote.add(n);
            Toast.makeText(this, "note is added", Toast.LENGTH_SHORT).show();
            myNote.setText("");

        }
    }
}
