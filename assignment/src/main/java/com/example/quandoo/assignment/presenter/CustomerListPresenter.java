package com.example.quandoo.assignment.presenter;

import com.example.quandoo.assignment.view.CustomerView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * The presenter interface as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 */
public interface CustomerListPresenter extends MvpPresenter<CustomerView> {

    /**
     * The method will fetch the list of customers over the network
     * or from database if available.
     * @param url url of the server to fetch the customer list
     */
    public void loadCustomers(String url);

    /**
     * The method decides the action to be taken when a list item is clicked.
     */
    public void takeAction();
}
