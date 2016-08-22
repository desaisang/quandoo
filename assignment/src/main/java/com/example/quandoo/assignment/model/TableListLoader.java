package com.example.quandoo.assignment.model;

import android.os.AsyncTask;

import com.example.quandoo.assignment.NetworkManager;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The class is a asynctask which is responsible for loading the table list from DB
 * if available otherwise it will fetch the list from the server.
 */
public class TableListLoader extends AsyncTask<String, Void, List<Integer>>{

    private ArrayList<Integer> mTableList = new ArrayList<Integer>();

    private TableListListener mTableListListener;

    /**
     * callback interface
     */
    public interface TableListListener {
        public void onSuccess(List<Integer> tableBookingList);
        public void onError(Exception e);
    }

    /**
     * Constructor
     * @param tableListListener callback instance
     */
    public TableListLoader(TableListListener tableListListener) {
        mTableListListener = tableListListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Integer> doInBackground(String... params) {
        DBManager databaseManager = DBManager.getDBManagerInstance();
        //check if we already have the status.
        List<Integer> tableList = databaseManager.getReservationStatus();

        if(tableList != null) {
            mTableList = new ArrayList<Integer>(tableList);
            return mTableList;
        }

        //fetch the status from Server
        InputStream ins = NetworkManager.get(params[0]);
        if(ins != null) {
            Gson gsonParser = new Gson();
            Reader streamReader = new InputStreamReader(ins);
            Boolean[] arr = gsonParser.fromJson(streamReader, Boolean[].class);
            for(Boolean status: arr) {
                mTableList.add((status)?1:0);
            }
            insertBookingStatusinDB(mTableList);
            return mTableList;
        } else {
            //TODO
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Integer> tableList) {
        super.onPostExecute(tableList);
        if((tableList != null) && (tableList.size() > 0)) {
            mTableListListener.onSuccess(tableList);
        }
        else {
            mTableListListener.onError(new Exception("oops, could not fetch the list"));
        }

    }

    /**
     * This method inserts the status of tables in DB.
     * @param bookingTableList booking status of all the tables
     */
    private void insertBookingStatusinDB(List<Integer> bookingTableList) {
        DBManager databaseMgr = DBManager.getDBManagerInstance();
        int ID = 0;
        for(Integer status: bookingTableList) {
            databaseMgr.insertTableBookingStatus(Integer.toString(ID), status);
            ID++;
        }
    }
}
