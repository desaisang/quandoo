package com.example.quandoo.assignment.presenter;

import com.example.quandoo.assignment.view.CustomerView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * The interface.
 */
public interface CustomerListPresenter extends MvpPresenter<CustomerView> {

    public void loadCustomers(String url);

    public void takeAction();
}
