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
 * The class is asynctask responsible for loading the customer list from DB if available
 * otherwise it will fetch the list from the server.
 */
public class CustomerListLoader extends AsyncTask<String, Void, List<CustomerResponse>> {

    private ArrayList<CustomerResponse> mCustomerList;

    private CustomerListListener mCustomerListListener;

    private DBManager mDBManager;

    /**
     * CallBack interface
     */
    public interface CustomerListListener {
        void onSuccess(List<CustomerResponse> customerList);
        void onError(Exception e);

    }

    /**
     * Constructor
     * @param listener callback listener
     */
    public CustomerListLoader(CustomerListListener listener){
        mCustomerListListener = listener;
        mDBManager = DBManager.getDBManagerInstance();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<CustomerResponse> doInBackground(String... params) {
        List<CustomerResponse> list = mDBManager.getCustomerNames();

        if(list != null) {
            //The customer list is present in database.
            mCustomerList = new ArrayList<CustomerResponse>(list);
            return mCustomerList;
            }
        //Get the list from Server
        InputStream ins = NetworkManager.get(params[0]);
        //parse the inputstream
        if(ins != null) {
            Gson gsonParser = new Gson();
            Reader streamReader = new InputStreamReader(ins);
            CustomerResponse[] crArray = gsonParser.fromJson(streamReader, CustomerResponse[].class);
            mCustomerList = new ArrayList<CustomerResponse>(Arrays.asList(crArray));
            return mCustomerList;
        }
        else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<CustomerResponse> customerResponseJsons) {
        super.onPostExecute(customerResponseJsons);
        if(customerResponseJsons != null && customerResponseJsons.size() > 0) {
            mCustomerListListener.onSuccess(customerResponseJsons);
        }
        else {
            mCustomerListListener.onError(new Exception("oops, could not fetch the list"));
        }
    }
}
