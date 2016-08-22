package com.example.quandoo.assignment.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This singleton class manages the database operations for OFFLINE access.
 * This class is responsible for insertion/retrieval of the data from database
 */
public class DBManager {

    private static DBManager mDBMgr;

    public static final int TABLE_AVAILABLE = 1;

    public static final int TABLE_RESERVED = 2;


    private DBHelper mDBHelper;

    private SQLiteDatabase database;

    private DBManager() {

    }

    /**
     * This method returns the DBManager instance.
     * @return DBManager object
     */
    public static DBManager getDBManagerInstance() {
        if(mDBMgr == null) {
            mDBMgr = new DBManager();
        }
        return mDBMgr;
    }

    /**
     * This method creates the database.
     * @param cxt Context object
     * @throws SQLException
     */

    public void openDB(Context cxt) throws SQLException {
        mDBHelper = new DBHelper(cxt);
        database = mDBHelper.getWritableDatabase();
    }

    /**
     * This method will close the database
     */

    public void close() {
        mDBHelper.close();
    }

    /**
     * This method inserts the customer record in table.
     * @param ID Customer ID
     * @param firstName first name of customer
     * @param lastName last name of customer
     */

    public void insertCustomerRecord(String ID, String firstName, String lastName) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, ID);
        contentValue.put(DBHelper.FIRSTNAME, firstName);
        contentValue.put(DBHelper.LASTNAME, lastName);

        database.insert(DBHelper.TABLE_NAME, null, contentValue);
    }

    /**
     * This method retrieves the list of customers from database table.
     * @return List of customer objects
     */

    public List<CustomerResponse> getCustomerNames() {
        String[] columns = new String[] {
                DBHelper.ID, DBHelper.FIRSTNAME, DBHelper.LASTNAME };
        Cursor cursor = database.query(DBHelper.TABLE_NAME,columns, null, null, null, null, null);
        ArrayList<CustomerResponse> customerList = null;
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                customerList = new ArrayList<CustomerResponse>();

                while (cursor.moveToNext()) {
                    String ID = cursor.getString(cursor.getColumnIndex(DBHelper.ID));
                    String firstName = cursor.getString(cursor.getColumnIndex(DBHelper.FIRSTNAME));
                    String lastName = cursor.getString(cursor.getColumnIndex(DBHelper.LASTNAME));

                    customerList.add(new CustomerResponse(ID, firstName, lastName));
                }
            }
            cursor.close();
        }
        return customerList;
    }

    /**
     * This method inserts the booked table status in table database.
     * @param status boolean value. true if the table is available for reservation
     */
    public void insertTableBookingStatus(String ID, int status){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, ID);
        contentValue.put(DBHelper.BOOKED_STATUS, status);

        database.insert(DBHelper.BOOKING_TABLE_NAME, null, contentValue);
    }

    /**
     * This method updates the booking status of a table when user clicks on it.
     * @param ID table ID
     * @param status true if the table needs to be set as booked false otherwise
     */
    public void updateTableBookingStatus(int ID, int status){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.BOOKED_STATUS, status);

     database.update(DBHelper.BOOKING_TABLE_NAME, contentValue, "id = ?", new String[]{Integer.toString(ID)});
    }
    /**
     * This method returns the reservation status of all the tables.
     * The status of a table is true if already reserved otherwise it is false.
     * @return status list for all the tables
     */
    public List<Integer> getReservationStatus() {
        String[] columns = new String[] {DBHelper.BOOKED_STATUS};

        Cursor cursor = database.query(DBHelper.BOOKING_TABLE_NAME,columns, null, null, null, null, null);
        ArrayList<Integer> bookingTableList = null;
        if(cursor != null) {
            if (cursor.getCount() > 0) {
                bookingTableList = new ArrayList<Integer>();

                while (cursor.moveToNext()) {
                    int bookedStatus = cursor.getInt(cursor.getColumnIndex(DBHelper.BOOKED_STATUS));
                   // bookingTableList.add((bookedStatus == 1)?true:false);
                    bookingTableList.add(bookedStatus);
                }
            }
            cursor.close();
        }
        return bookingTableList;
    }

    /**
     * This method resets the reservations made by the user
     */
    public void resetReservations() {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.BOOKED_STATUS, TABLE_AVAILABLE);

        String whereClause = "booked_status=?";

        database.update(DBHelper.BOOKING_TABLE_NAME, contentValue, whereClause, new String[]{Integer.toString(TABLE_RESERVED)});

    }
}

