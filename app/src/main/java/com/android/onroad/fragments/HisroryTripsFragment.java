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
import com.android.onroad.beans.Note;
import com.android.onroad.beans.Trip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HisroryTripsFragment extends Fragment {

    private Unbinder unbinder;

    List<Trip> trips = new ArrayList<>();
    Trip trip = new Trip();
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
        ArrayList<Note> myNotes=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            trip = new Trip();
            trip.setName("school");
            myNotes.add(new Note(1,"mmmm"));
            myNotes.add(new Note(2,"llll"));
            myNotes.add(new Note(3,"ssss"));

            trip.setNotes(myNotes);
            trip.setStartPoint("giza");
            trip.setEndPoint("cairo");
            trip.setStatus("done");
            trip.setType("one way");


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
