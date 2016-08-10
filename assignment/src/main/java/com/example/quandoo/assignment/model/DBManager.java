package com.example.quandoo.assignment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the database operations for OFFLINE access.
 * This class is responsible for insertion/retrieval of the data from database
 */
public class DBManager {

    private static DBManager mDBMgr;

    private DBHelper mDBHelper;

    private Context mContext;

    private SQLiteDatabase database;

    private DBManager() {

    }

    public static DBManager getDBManagerInstance() {
        if(mDBMgr == null) {
            mDBMgr = new DBManager();
        }
        return mDBMgr;
    }

    public DBManager openDB(Context cxt) throws SQLException {
        mDBHelper = new DBHelper(cxt);
        database = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDBHelper.close();
    }

    public void insertCustomerRecord(String ID, String firstName, String lastName) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, ID);
        contentValue.put(DBHelper.FIRSTNAME, firstName);
        contentValue.put(DBHelper.LASTNAME, lastName);

        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    public List<CustomerResponseJson> getCustomerNames() {
        String[] columns = new String[] {
                DBHelper.ID, DBHelper.FIRSTNAME, DBHelper.LASTNAME };
        Cursor cursor = database.query(DBHelper.TABLE_NAME,columns, null, null, null, null, null);
        ArrayList<CustomerResponseJson> customerList = null;
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                customerList = new ArrayList<CustomerResponseJson>();

                while (cursor.moveToNext()) {
                    String ID = cursor.getString(cursor.getColumnIndex(DBHelper.ID));
                    String firstName = cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME));
                    String lastName = cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME));

                    customerList.add(new CustomerResponseJson(ID, firstName, lastName));
                }
            }
            cursor.close();
            cursor = null;
        }
        return customerList;
    }

    public void insertBookedTableStatus(boolean isTableAvailable){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.BOOKED_STATUS, (isTableAvailable)?1:0);

        database.insert(DBHelper.BOOKING_TABLE_NAME, null, contentValue);
    }

    public List<Boolean> getReservationStatus() {
        String[] columns = new String[] {DBHelper.BOOKED_STATUS};

        Cursor cursor = database.query(DBHelper.BOOKING_TABLE_NAME,columns, null, null, null, null, null);
        ArrayList<Boolean> bookingTableList = null;
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                bookingTableList = new ArrayList<Boolean>();

                while (cursor.moveToNext()) {
                    int bookedStatus = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOKED_STATUS));
                    bookingTableList.add((bookedStatus == 1)?true:false);
                }
            }
            cursor.close();
            cursor = null;
        }
        return bookingTableList;
    }
}

