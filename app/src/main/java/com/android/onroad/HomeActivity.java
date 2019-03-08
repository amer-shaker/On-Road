package com.android.onroad;

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

import com.android.onroad.adapters.MainViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.tab_main)
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
    MainViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initViews();
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
    @Optional
    @OnClick(R.id.fab_add_trip)
    public void add(View view) {
        Snackbar.make(constraint, "hello from fab man ", Snackbar.LENGTH_LONG).show();

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
        adapter = new MainViewPagerAdapter(manager);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history_trips));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.upcomming_string_key));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
