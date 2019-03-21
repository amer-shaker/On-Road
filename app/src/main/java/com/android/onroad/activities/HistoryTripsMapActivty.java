package com.android.onroad.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.android.onroad.R;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.VollyDao;
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
    Trip myTrip;

//should replaced with  array of history trips
    ArrayList<Trip> trips = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_trips_map_activty);
        mRequestQueue = VollyDao.getRequestQueue(this);
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        //dummy data

        mapFragment.getMapAsync(this);
        Trip t1=new Trip();
        Trip t2=new Trip();

        t1.setStartPointLatitude(30.109760);
        t1.setStartPointLongitude(31.247240);

        t1.setEndPointLongitude(30.797569);
        t1.setEndPointLatitude(29.753120);


        t2.setStartPointLatitude(29.86622312999999);
        t2.setStartPointLongitude(31.337005422999999);

        t2.setEndPointLongitude(31.2313116);
        t2.setEndPointLatitude(31.0445296);

        trips.add(t1);
        trips.add(t2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mapFragment.getMapAsync(HistoryTripsMapActivty.this);
                mProgressDialog = new ProgressDialog(HistoryTripsMapActivty.this);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.show();
            }
        }, 200);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        for (final Trip trip:trips) {

            mRequestQueue.add(
                    new JsonObjectRequest(
                            "https://maps.googleapis.com/maps/api/directions/json?origin="

                                    +trip.getStartCoordinates()
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
                                }
                            }
                    )
            );
            mRequests++;
            //String[] coordinates = myTrip.getStartCoordinates().split(",");
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(trip.getStartPointLatitude(), trip.getStartPointLongitude()), 10f));

        }



        mRequestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                mRequests--;
                if (mRequests == 0){

//                    mProgressDialog.dismiss();
                }
                //  mProgressDialog.dismiss();
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
}
