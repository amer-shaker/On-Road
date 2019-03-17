package com.android.onroad.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTripActivity extends AppCompatActivity {

    private static final String TAG = "AddTripActivity";

    Button btnTimePicker, btnDatePicker, AddTrip;
    TextView txtDate, txtTime;
    Spinner spnRepeat, spnStatus;

    EditText tripName, myNote;
    Date date;
    Date myDateCheck;
    boolean isUsed = false;

    String myStartPoint = "", myEndPoint = "", sLat = "", sLong = "", ePoint = "", eLat = "", eLong = "";
    double mysLat, mysLong, myeLat, myeLong;
    ArrayList<Note> myArrayNote = new ArrayList<>();

    Date myDate = new Date();

    Trip editObj;
    Date myTime;
    String myStatus, myRepeat;


    String myTripName;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private List<Trip> trips = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        btnDatePicker = findViewById(R.id.btnAddDate);
        btnTimePicker = findViewById(R.id.btnAddTime);
        txtDate = findViewById(R.id.in_date_add);
        txtTime = findViewById(R.id.in_time_add);

        tripName = findViewById(R.id.txtTripName);
        AddTrip = findViewById(R.id.btnAddTrip);
        spnRepeat = findViewById(R.id.spnRepeat);
        spnStatus = findViewById(R.id.spnStatus);
        myNote = findViewById(R.id.txtAddNote);
        PlaceAutocompleteFragment autocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtStartPoint);
        PlaceAutocompleteFragment autocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.txtEndPoint);

         editObj = getIntent().getParcelableExtra("myTrip");
        if(editObj!=null)
        {
            isUsed = true;
            // putDataInFields();
            AddTrip.setText("Update Trip");
            tripName.setText(editObj.getName());
            autocompleteFragment1.setText(editObj.getStartPoint());
            autocompleteFragment2.setText(editObj.getEndPoint());
            myDate = editObj.getDate();

            String editStatus = "Round Trip";//editObj.getStatus(); //the value you want the position for
            ArrayAdapter myAdap;
            myAdap= (ArrayAdapter) spnStatus.getAdapter(); //cast to an ArrayAdapter
            int spinnerPosition ;
            spinnerPosition= myAdap.getPosition(editStatus);
            spnStatus.setSelection(spinnerPosition);

            String editRepeat = editObj.getType(); //the value you want the position for
            myAdap = (ArrayAdapter) spnRepeat.getAdapter(); //cast to an ArrayAdapter
            spinnerPosition = myAdap.getPosition(editRepeat);
            spnStatus.setSelection(spinnerPosition);

            txtDate.setText(myDate.getDay() + "-" + (myDate.getMonth() + 1) + "-" + myDate.getYear());
            txtTime.setText(myDate.getHours() + ":" + myDate.getMinutes());

        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node));

        AddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tripName.getText().toString().equals(""))

                    Toast.makeText(AddTripActivity.this, "enter the trip name", Toast.LENGTH_SHORT).show();

                if (myStartPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Start Point", Toast.LENGTH_SHORT).show();

                if (myEndPoint.equals(""))
                    Toast.makeText(AddTripActivity.this, "enter end Point", Toast.LENGTH_SHORT).show();

                if (txtDate.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Date", Toast.LENGTH_SHORT).show();

                if (txtTime.getText().equals(""))
                    Toast.makeText(AddTripActivity.this, "enter Time", Toast.LENGTH_SHORT).show();
                else {

                    if (isUsed) {
                        Trip trip = editObj;
                        myTripName = tripName.getText().toString();
                        trip.setName(myTripName);
                        trip.setDate(myDate);
                        trip.setEndPoint(myEndPoint);
                        trip.setStartPoint(myStartPoint);
                        trip.setEndPointLatitude(myeLat);
                        trip.setEndPointLongitude(myeLong);
                        trip.setStartPointLatitude(mysLat);
                        trip.setStartPointLongitude(mysLong);
                        trip.setNotes(myArrayNote);
                        trip.setType(myRepeat);
                        trip.setStatus(myStatus);

                    } else {
                        Trip trip = new Trip();
                        myTripName = tripName.getText().toString();
                        trip.setName(myTripName);
                        trip.setDate(myDate);
                        trip.setEndPoint(myEndPoint);
                        trip.setStartPoint(myStartPoint);
                        trip.setEndPointLatitude(myeLat);
                        trip.setEndPointLongitude(myeLong);
                        trip.setStartPointLatitude(mysLat);
                        trip.setStartPointLongitude(mysLong);
                        trip.setNotes(myArrayNote);
                        trip.setType(myRepeat);
                        trip.setStatus(myStatus);

                        mTripsDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid())
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

                        Utility.setAlarmTime(AddTripActivity.this, trip, myDate.getHours(), myDate.getMinutes(), myDate.getMonth());


//                Intent myIntent = new Intent(AddTripActivity.this,HomeActivity.class);
//                myIntent.putExtra("myTrip",myTrip);
//                startActivity(myIntent);
                    }
                }
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
                        myTime = new Date();
                        myTime.setHours(selectedHour);
                        myTime.setMinutes(selectedMinute);

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
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

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
                                if (myDateCheck.equals(null)) {
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
                datePickerDialog.show();
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
                    myStartPoint = place.getName().toString();
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
        if (autocompleteFragment2 != null)
            autocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place./
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
                    // TODO: Handle the error.
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

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Log.i(TAG, "onChildAdded()");

                    Trip trip = dataSnapshot.getValue(Trip.class);
                    if (trip != null) {
                        trips.add(trip);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AddTripActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            };
            mTripsDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mTripsDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }
}