package com.android.onroad.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_Helper extends SQLiteOpenHelper {
    Context myContext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "OnRoad";

    public DB_Helper(Context context) {
        super(context, "", null, DATABASE_VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + Constants.TRIP_TABLE + "(" + Constants.TRIP_ID_COL + "integer primary key autoincrement ,"+
//                Constants.TRIP_NAME_COL+"varchar(255),"+S);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
