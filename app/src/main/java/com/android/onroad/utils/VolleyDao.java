package com.android.onroad.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyDao {

    private VolleyDao()
    {

    }

    private static RequestQueue mRequestQueue;

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            synchronized (VolleyDao.class) {

                if (mRequestQueue == null)
                    mRequestQueue = Volley.newRequestQueue(context);
            }
        }

        return mRequestQueue;
    }
}


