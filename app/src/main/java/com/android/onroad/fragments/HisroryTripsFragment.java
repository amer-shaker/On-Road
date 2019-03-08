package com.android.onroad.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.onroad.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HisroryTripsFragment extends Fragment {

    private Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_history,container,false);
        unbinder= ButterKnife.bind(this,view);
        return view;
    }
}
