package com.example.quandoo.assignment.presenter;

import android.content.Context;

import com.example.quandoo.assignment.view.TableView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * The presenter interface as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 */
public interface TableListPresenter extends MvpPresenter<TableView>{
    /**
     * The method will fetch the reservation status of tables over the network
     * or from database if available.
     * @param url url of the server to fetch the customer list
     */
    public void loadTables(String url);

    /**
     * This method updates the booking status of a table in DB
     * @param tableID ID of the table
     * @param status Integer indicating if a table is available or unavailable or reserved
     */
    public void updateTableStatus(int tableID, int status);

    public void resetBookingStatus(Context cxt);
}
