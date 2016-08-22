package com.example.quandoo.assignment.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class for Database handling
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "CUSTOMERS";
    public static final String BOOKING_TABLE_NAME = "BOOKINGSTATUS";
    public static final String ID = "id";
    public static final String _ID = "_id";
    public static final String FIRSTNAME = "first_name";
    public static final String LASTNAME = "last_name";
    public static final String BOOKED_STATUS = "booked_status";

    static final String DB_NAME ="quandoo.db";
    static final int DB_VERSION = 1;

    private static final String CREATE_CUSTOMER_TABLE = "create table " + TABLE_NAME + "(" + ID
            + " TEXT, " + FIRSTNAME + " TEXT NOT NULL, " + LASTNAME + " TEXT NOT NULL);";

    private static final String CREATE_BOOKING_TABLE = "create table " + BOOKING_TABLE_NAME + "(" +
            ID + " TEXT, "+ BOOKED_STATUS + " INTEGER);";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CUSTOMER_TABLE);
        db.execSQL(CREATE_BOOKING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(CREATE_CUSTOMER_TABLE);

        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE_NAME);
        db.execSQL(CREATE_BOOKING_TABLE);

    }
}
