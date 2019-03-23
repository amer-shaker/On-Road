package com.android.onroad.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.adapters.CanceledTripsAdapter;
import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CanceledTripsFragment extends Fragment {

    private View view;
    private List<Trip> trips = new ArrayList<>();

    private RecyclerView recyclerView;
    private CanceledTripsAdapter mCanceledTripsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "CanceledTripsFragment";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trip, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = Utility.getFirebaseDatabaseInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node))
                    .child(mFirebaseAuth.getCurrentUser().getUid());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mCanceledTripsAdapter = new CanceledTripsAdapter(getActivity(), trips);
        recyclerView.setAdapter(mCanceledTripsAdapter);
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
                        if (trip.getStatus().equals(Trip.CANCELED_TRIP)) {
                            trips.add(trip);
                            mCanceledTripsAdapter.updateList(trips);
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
                    Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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