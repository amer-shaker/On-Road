package com.android.onroad.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.onroad.R;
import com.android.onroad.activities.AddTripActivity;
import com.android.onroad.adapters.UpcommingTripsAdapter;
import com.android.onroad.beans.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpCommingTripsFragment extends Fragment {
    List<Trip> trips = new ArrayList<>();
    Trip trip = new Trip();
    UpcommingTripsAdapter adapter;

    @BindView(R.id.home_activity_constrint)
    ConstraintLayout constraint;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framgent_upcomming, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < 5; i++) {
            trip = new Trip();
            trip.setTripName("school");
            trip.setStartPoint("giza");
            trip.setEndPoint("cairo");
            trips.add(trip);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new UpcommingTripsAdapter(getActivity());
        adapter.setItems(trips);
        recyclerView.setAdapter(adapter);

    }

    @OnClick(R.id.fab_add_trip)
    public void add(View view) {
        startActivity(new Intent(getActivity(), AddTripActivity.class));
//                Utility.pushNotification(getActivity(),"");

    }
}
