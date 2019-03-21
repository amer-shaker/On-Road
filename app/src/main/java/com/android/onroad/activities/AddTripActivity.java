package com.android.onroad.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Note;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;
import com.android.onroad.utils.Utility;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddTripActivity extends AppCompatActivity {

    private static final String TAG = "AddTripActivity";

    private Button btnTimePicker, btnDatePicker, addTripButton;
    private TextView txtDate, txtTime;
    private Spinner spnRepeat, spnStatus;

    private EditText tripName, myNote;
    private Date date;
    private Date myDateCheck;
    boolean isUsed = false;

    private String myStartPoint = "", myEndPoint = "", sLat = "", sLong = "", ePoint = "", eLat = "", eLong = "";
    double mysLat, mysLong, myeLat, myeLong;
    private ArrayList<Note> myArrayNote = new ArrayList<>();

    private Date myDate = new Date();

    private Trip editObj;
    private String myStatus, myRepeat;


    private String myTripName;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;

    private ChildEventListener mChildEventListener;
    PlaceAutocompleteFragment autocompleteFragment1, autocompleteFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        btnDatePicker = findViewById(R.id.btnAddDate);
        btnTimePicker = findViewById(R.id.btnAddTime);
        txtDate = findViewById(R.id.in_date_add);
        txtTime = findViewById(R.id.in_time_add);
        tripName = findViewById(R.id.txtTripName);
        addTripButton = findViewById(R.id.btnAddTrip);
        spnRepeat = findViewById(R.id.spnRepeat);
        spnStatus = findViewById(R.id.spnStatus);
        myNote = findViewById(R.id.txtAddNote);
        myDate = new Date();
        autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtStartPoint);
        autocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtEndPoint);

        editObj = getIntent().getParcelableExtra(Constants.TRIP);
        if (editObj != null) {
            isUsed = true;
            // putDataInFields();
            addTripButton.setText("Update Trip");
            tripName.setText(editObj.getName());
            autocompleteFragment1.setText(editObj.getStartPoint());
            autocompleteFragment2.setText(editObj.getEndPoint());
            if (myDate != null)
                Toast.makeText(this, "my date not null", Toast.LENGTH_SHORT).show();
            myDate = editObj.getDate();
            if (myDate != null)
                Toast.makeText(this, "my date not null", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "my date is null ", Toast.LENGTH_SHORT).show();
            String editStatus = "Round Trip";//editObj.getStatus(); //the value you want the position for
            ArrayAdapter myAdap;
            myAdap = (ArrayAdapter) spnStatus.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition;
            spinnerPosition = myAdap.getPosition(editStatus);
            spnStatus.setSelection(spinnerPosition);

            String editRepeat = editObj.getType(); //the value you want the position for
            myAdap = (ArrayAdapter) spnRepeat.getAdapter(); //cast to an ArrayAdapter
            spinnerPosition = myAdap.getPosition(editRepeat);
            spnStatus.setSelection(spinnerPosition);
            if (myDate != null) {
                txtDate.setText(myDate.getDay() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getYear() + " ");
                txtTime.setText(myDate.getHours() + ":" + myDate.getMinutes() + " ");
            }
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node));

        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tripName.getText().toString().equals("")) {
                    Toast.makeText(AddTripActivity.this, "enter the trip name", Toast.LENGTH_SHORT).show();
                }

                if (myStartPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Start Point", Toast.LENGTH_SHORT).show();

                if (myEndPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter end Point", Toast.LENGTH_SHORT).show();

                if (txtDate.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Date", Toast.LENGTH_SHORT).show();

                if (txtTime.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Time", Toast.LENGTH_SHORT).show();
                else {
                    Trip trip = new Trip();
                    String tripId = mTripsDatabaseReference.getRef().push().getKey();
                    trip.setTripId(tripId);
                    myTripName = tripName.getText().toString();
                    trip.setName(myTripName);
                    trip.setTime(String.valueOf(myDate.getTime()));
                    trip.setEndPoint(myEndPoint);
                    trip.setStartPoint(myStartPoint);
                    trip.setEndPointLatitude(myeLat);
                    trip.setEndPointLongitude(myeLong);
                    trip.setStartPointLatitude(mysLat);
                    trip.setStartPointLongitude(mysLong);
                    trip.setNotes(myArrayNote);
                    trip.setType(myRepeat);
                    trip.setStatus(Trip.UPCOMING_TRIP);

                    int id = (int) (myDate.getTime() + myDate.getMonth() + myDate.getYear() + myDate.getSeconds());
                    trip.setAlarmId(id);
                    if (isUsed) {
                        Utility.setAlarmTime(AddTripActivity.this, trip, myDate.getHours(), myDate.getMinutes(), myDate.getMonth(), trip.getAlarmId());
                    } else {
                        Utility.setAlarmTime(AddTripActivity.this, trip, myDate.getHours(), myDate.getMinutes(), myDate.getMonth(), id);
                    }

                    mTripsDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid())
                            .child(tripId)
                            .setValue(trip)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddTripActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddTripActivity.this, "Something, went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        spnStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                myStatus = spnStatus.getItemAtPosition(position).toString();
                Toast.makeText(AddTripActivity.this, myStatus, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
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

                Calendar mCalendar = Calendar.getInstance();
                final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
                final int minute = mCalendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        txtTime.setText(selectedHour + ":" + selectedMinute);
                        myDate = new Date();
                        myDate.setHours(selectedHour);
                        myDate.setMinutes(selectedMinute);

                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {
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
                                if (myDateCheck == null) {
                                    try {
                                        myDateCheck = format.parse(timeStamp);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    if (myDateCheck.before(date)) {
                                        Toast.makeText(view.getContext(), "Enter Valid Date", Toast.LENGTH_SHORT).show();
                                    } else {
                                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                        myDate.setMonth(monthOfYear + 1);
                                        myDate.setYear(year);
                                        myDate.setDate(dayOfMonth);
                                    }
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtStartPoint);
        if (autocompleteFragment1 != null)
            autocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {

                    Log.i(TAG, "Place: " + place.getName());
                    myStartPoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    mysLat = myLatLong.latitude;
                    mysLong = myLatLong.longitude;
                    sLat = mysLat + "";
                    sLong = mysLong + "";
                }

                @Override
                public void onError(com.google.android.gms.common.api.Status status) {
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();


        autocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtEndPoint);
        if (autocompleteFragment2 != null)
            autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    Log.i(TAG, "Place: " + place.getName());
                    String placeName = place.getName().toString();
                    Toast.makeText(AddTripActivity.this, "the place is " + placeName, Toast.LENGTH_SHORT).show();
                    myEndPoint = place.getName().toString();
                    LatLng myLatLong = place.getLatLng();
                    myeLat = myLatLong.latitude;
                    myeLong = myLatLong.longitude;
                    eLat = myeLat + "";
                    eLong = myeLong + "";
                }

                @Override
                public void onError(Status status) {
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        else Toast.makeText(this, "Problem with loading page", Toast.LENGTH_LONG).show();
    }

    public void addNote(View view) {
        if (myNote.getText().toString().equals(""))
            Toast.makeText(this, "enter Note", Toast.LENGTH_SHORT).show();
        else {
            Note n = new Note();
            n.setNote(myNote.getText().toString());
            myArrayNote.add(n);
            Toast.makeText(this, "note is added", Toast.LENGTH_SHORT).show();
            myNote.setText("");
        }
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