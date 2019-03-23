package com.android.onroad;

import android.content.Intent;
import android.app.Service;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.onroad.beans.Trip;
import com.android.onroad.utils.Constants;

import java.util.ArrayList;

public class FloatWidgetIntentService extends Service {
    private WindowManager mWindowManager;
    private View floatView;
    boolean isExpand = false;
    WindowManager.LayoutParams params;
    ListView myNoteslst;
    private int initialX = 0;
    private int initialY = 0;

    private float initialTouchY = 0, initialTouchX = 0;
    private long time_start = 0, time_end = 0;

    Trip trip;

    public FloatWidgetIntentService() {

    }

/*
>>>>>>> 889de85e1fb2760ea1852d337b79b830769c0993
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatView != null) mWindowManager.removeView(floatView);

    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        if (intent.getExtras().getParcelable(Constants.TRIP) != null) {
            trip = intent.getExtras().getParcelable(Constants.TRIP);
            floatView = LayoutInflater.from(this).inflate(R.layout.float_widget, null);
            myNoteslst = floatView.findViewById(R.id.notesListForWidget);

        }
        ArrayList<String> myNotes = new ArrayList<>();
        if (trip.getNotes() != null) {
            for (int i = 0; i < trip.getNotes().size(); i++) {
                myNotes.add(trip.getNotes().get(i).getNote());
            }

        }


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, myNotes);
        myNoteslst.setAdapter(adapter);


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);
        } else {
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                            | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    PixelFormat.TRANSLUCENT);
        }

        params.gravity = Gravity.START | Gravity.TOP;
        params.x = 0;
        params.y = 100;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(floatView, params);

        floatView.findViewById(R.id.collapsed_iv).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        time_start = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX - 60);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY - 60);
                        //Update the layout with new X & Y coordinate
                        break;
                    case MotionEvent.ACTION_UP:
                        DisplayMetrics metrics = new DisplayMetrics();
                        mWindowManager.getDefaultDisplay().getMetrics(metrics);
                        int xInches = metrics.widthPixels;
                        if (params.x > xInches / 2) {
                            params.x = xInches - floatView.getWidth() - 10;
                        } else {
                            params.x = 0;
                        }
                        time_end = System.currentTimeMillis();
                        if (time_end - time_start < 500) {
                            if (!isExpand) {
                                myNoteslst.setVisibility(View.VISIBLE);
                                isExpand = !isExpand;
                            } else {
                                myNoteslst.setVisibility(View.GONE);
                                isExpand = !isExpand;
                            }
                        }
                        break;
                    default:
                        return false;
                }
                mWindowManager.updateViewLayout(floatView, params);
                return true;
            }
        });

        ImageView closeButtonCollapsed = (ImageView) floatView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWidgetIntentService.this.stopService(intent);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    public FloatWidgetIntentService getService() {

        return FloatWidgetIntentService.this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}