package com.android.onroad.activities;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.android.onroad.R;
import com.android.onroad.adapters.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.tab_main_view)
    TabLayout tabLayout;

    @BindView(R.id.main_pager)
    ViewPager pager;
    @BindView(R.id.menu_img)
    ImageView imgMenue;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.constrint)
    ConstraintLayout constraint;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;


    FragmentManager manager;
    ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
//        Trip trip = getIntent().getParcelableExtra("myTrip");
//        Toast.makeText(this, trip.getStartPoint() + " "+ trip.getTripName(), Toast.LENGTH_SHORT).show();
        imgMenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerVisible(Gravity.LEFT)) {
                    drawer.closeDrawer(Gravity.LEFT);
                } else {
                    drawer.openDrawer(Gravity.LEFT);
                }
                Snackbar.make(constraint, "", Snackbar.LENGTH_LONG).show();

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    void initViews() {
        manager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(manager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.upcomming_string_key));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history_trips));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @OnClick(R.id.linear_logout)
    void logout(View view) {
        Snackbar.make(constraint, "logout", Snackbar.LENGTH_LONG).show();

    }

    @OnClick(R.id.linear_profile)
    void showProfile(View view) {
        Snackbar.make(constraint, "showProfile", Snackbar.LENGTH_LONG).show();

    }

    @OnClick(R.id.linear_sync)
    void syncData(View view) {
        Snackbar.make(constraint, "syncData", Snackbar.LENGTH_LONG).show();

    }


}
