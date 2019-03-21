package com.android.onroad.delegates;

import com.android.onroad.beans.Trip;

import java.util.List;

public interface BindDataDelegate {
    void onBindData(List<Trip> trips);
}
