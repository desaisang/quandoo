package com.example.quandoo.assignment.presenter;

import android.util.Log;

import com.example.quandoo.assignment.model.DBManager;
import com.example.quandoo.assignment.model.TableListLoader;
import com.example.quandoo.assignment.view.TableView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Concrete presenter class which is responsible for handling Table list screen.
 * It is implemented as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 *
 */
public class SimpleTableListPresenter extends MvpBasePresenter<TableView> implements TableListPresenter{
    private static final String TAG = "SiTableListPresenter";
    private TableListLoader mTableListLoader;

    @Override
    public void loadTables(String url) {
        Log.d(TAG, "loadTables");

        getView().showLoading(false);

        mTableListLoader = new TableListLoader(new TableListLoader.TableListListener() {
            @Override
            public void onSuccess(List<Boolean> tableBookingList) {
                insertBookingStatusinDB(tableBookingList);
                if (isViewAttached()) {
                    getView().setData(tableBookingList);
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
        mTableListLoader.execute(url);
    }
    private void insertBookingStatusinDB(List<Boolean>bookingTableList) {
        DBManager databaseMgr = DBManager.getDBManagerInstance();
        for(Boolean status: bookingTableList) {
            databaseMgr.insertBookedTableStatus(status);
        }
    }
}
