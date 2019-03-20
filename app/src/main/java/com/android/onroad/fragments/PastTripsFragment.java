package com.android.onroad.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.onroad.R;
import com.android.onroad.adapters.HistoryTripsAdapter;
import com.android.onroad.beans.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PastTripsFragment extends Fragment {

    private Unbinder unbinder;
    List<Trip> trips = new ArrayList<>();

    HistoryTripsAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private static final String TAG = "PastTripsFragment";

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTripsDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);
        unbinder = ButterKnife.bind(this, view);

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mTripsDatabaseReference = mFirebaseDatabase.getReference().child(getString(R.string.trips_database_node))
                    .child(mFirebaseAuth.getCurrentUser().getUid());
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new HistoryTripsAdapter(getActivity());
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
                        if (trip.getStatus().equals(Trip.HISTORY_TRIP)) {
                            trips.add(trip);
                            adapter.updateList(trips);
                            recyclerView.setAdapter(adapter);
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