package com.android.onroad.fragments;

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
import com.android.onroad.adapters.HistoryTripsAdapter;
import com.android.onroad.models.TripModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HisroryTripsFragment extends Fragment {

    private Unbinder unbinder;

    List<TripModel> trips = new ArrayList<>();
    TripModel trip = new TripModel();
    HistoryTripsAdapter adapter;

    @BindView(R.id.fragement_history_constraint)
    ConstraintLayout constraint;
    @BindView(R.id.recycler_main_history)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_history,container,false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (int i = 0; i < 5; i++) {
            trip = new TripModel();
            trip.setName("school");

            trip.setStartPoint("giza");
            trip.setEndPoint("cairo");
            trips.add(trip);
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new HistoryTripsAdapter(getActivity());
        adapter.setItems(trips);
        recyclerView.setAdapter(adapter);

    }

}
