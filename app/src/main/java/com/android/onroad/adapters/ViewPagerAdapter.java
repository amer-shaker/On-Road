package com.android.onroad.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.onroad.fragments.HisroryTripsFragment;
import com.android.onroad.fragments.UpCommingTripsFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new UpCommingTripsFragment();

            case 0:
                return new HisroryTripsFragment();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
