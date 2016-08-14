package com.example.quandoo.assignment.presenter;

import android.util.Log;

import com.example.quandoo.assignment.model.CustomerListLoader;
import com.example.quandoo.assignment.model.CustomerResponseJson;
import com.example.quandoo.assignment.model.DBManager;
import com.example.quandoo.assignment.view.CustomerView;
import com.example.quandoo.assignment.NetworkManager;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.io.InputStream;
import java.util.List;

/**
 * Concrete presenter class for handling the Customer list screen.
 * It is implemented as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 */
public class SimpleCustomerListPresenter extends MvpBasePresenter<CustomerView> implements CustomerListPresenter {

    private static final String TAG = "SiCustomerListPresenter";

    private CustomerListLoader mCustomerListLoaderTask;

    @Override
    public void loadCustomers(String url) {
        Log.d(TAG, "loadCustomers");
        getView().showLoading(false);

        mCustomerListLoaderTask = new CustomerListLoader(new CustomerListLoader.CustomerListListener() {
            @Override
            public void onSuccess(List<CustomerResponseJson> customerList) {
                insertCustomerListinDB(customerList);
                if (isViewAttached()) {
                    getView().setData(customerList);
                    getView().showContent();
                }
            }

            @Override
            public void onError(Exception e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                }
            }
        });

        mCustomerListLoaderTask.execute(url);
    }

    @Override
    public void takeAction() {
        getView().moveToNextView();
    }

    /**
     * This method will insert the list of customers into database.
     * The list is intially fetched from server.
     * @param customerList list of CustomerResponseJson objects.
     */
    private void insertCustomerListinDB(List<CustomerResponseJson> customerList) {
        DBManager databaseMgr = DBManager.getDBManagerInstance();
        for(CustomerResponseJson customer:customerList){
            databaseMgr.insertCustomerRecord(customer.mID, customer.mcustomerFirstName, customer.mcustomerLastName);
        }

    }

    /**
     * This method will ask network manager to get the list of customers from the network.
     * @param url url of the server to get the list of customers.
     * @return customer list stream
     */
    private InputStream getCustomerListFromNetwork(String url) {
        return NetworkManager.get(url);
    }

    @Override
    public void attachView(CustomerView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (mCustomerListLoaderTask != null) {
                mCustomerListLoaderTask.cancel(true);
            }
        }
    }
}
