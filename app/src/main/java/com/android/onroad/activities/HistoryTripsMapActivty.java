package com.android.onroad.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Utility;
import com.android.onroad.utils.VolleyDao;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HistoryTripsMapActivty extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ProgressDialog mProgressDialog;
    private int mRequests;
    private RequestQueue mRequestQueue;


    //should replaced with  array of history trips
    ArrayList<Trip> trips = new ArrayList<>();

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;
    private ChildEventListener mChildEventListener;

    private static final String TAG = "HistoryTripsMapActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trips_map_activty);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = Utility.getFirebaseDatabaseInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node))
                    .child(mFirebaseAuth.getCurrentUser().getUid());
        }


        mRequestQueue = VolleyDao.getRequestQueue(this);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //dummy data

        mapFragment.getMapAsync(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapFragment.getMapAsync(HistoryTripsMapActivty.this);
                mProgressDialog = new ProgressDialog(HistoryTripsMapActivty.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(true);

                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
            }
        }, 200);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        for (final Trip trip : trips) {

            mRequestQueue.add(
                    new JsonObjectRequest(
                            "https://maps.googleapis.com/maps/api/directions/json?origin="

                                    + trip.getStartCoordinates()
                                    + "&destination="
                                    + trip.getDestinationCoordinates()
                                    + "&key=AIzaSyC4X5tDaquLpuYFDXRsxYAxcl26I1E_KSs",
                            null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    int colors[] = {Color.RED, Color.BLACK, Color.MAGENTA, Color.DKGRAY, Color.GRAY, Color.MAGENTA, Color.YELLOW, Color.BLUE};
                                    Random rand = new Random();
                                    int randomColor = colors[rand.nextInt(colors.length)];
                                    drawPath(response, mMap, randomColor);
                                    LatLng latLng = new LatLng(trip.getStartPointLatitude(), trip.getStartPointLongitude());
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.title("Start point");

                                    markerOptions.position(latLng);

                                    LatLng latLngEnd = new LatLng(trip.getEndPointLatitude(), trip.getEndPointLongitude());
                                    MarkerOptions markerOptionsEnd = new MarkerOptions();
                                    markerOptionsEnd.title("End point");

                                    markerOptionsEnd.position(latLngEnd);


                                    mMap.addMarker(markerOptions);
                                    mMap.addMarker(markerOptionsEnd);

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("VollyError", error.getCause() + "");
                                }
                            }
                    )
            );
            mRequests++;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(trip.getStartPointLatitude(), trip.getStartPointLongitude()), 10f));

        }


        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                mRequests--;
                if (mRequests == 0) {

                    if (mProgressDialog != null)
                        mProgressDialog.dismiss();
                }


            }
        });

    }


    public void drawPath(JSONObject result, GoogleMap Map, int color) {

        try {
            JSONArray routeArray = result.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");
            List<LatLng> list = decodePoly(encodedString);
            Map.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(10)
                    .color(color)
                    .geodesic(true)

            );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
        attachDatabaseReadListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        trips.clear();
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
                        if (trip.getStatus().equals(Trip.PAST_TRIP)) {
                            trips.add(trip);
                        }
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
                    Toast.makeText(HistoryTripsMapActivty.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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