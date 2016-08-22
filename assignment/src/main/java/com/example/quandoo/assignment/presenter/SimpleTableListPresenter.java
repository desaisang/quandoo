package com.example.quandoo.assignment.presenter;

import android.content.Context;
import android.util.Log;

import com.example.quandoo.assignment.ResetBookingWatcher;
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
    private static String TABLE_STRING = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/table-map.json";
    private TableListLoader mTableListLoader;
    private ResetBookingWatcher mResetBookingWatcher = new ResetBookingWatcher(new ResetBookingWatcher.CallBack() {
        @Override
        public void onEventReceived() {
            DBManager DBMgr = DBManager.getDBManagerInstance();
            DBMgr.resetReservations();

            loadTables(TABLE_STRING);
        }
    });

    @Override
    public void loadTables(String url) {
        Log.d(TAG, "loadTables");
        if(isViewAttached()) {
            getView().showLoading(false);
        }

        mTableListLoader = new TableListLoader(new TableListLoader.TableListListener() {
            @Override
            public void onSuccess(List<Integer> tableBookingList) {
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

    /**
     * This method updates the booking status of a table in DB
     * @param tableID ID of the table
     * @param status boolean indicating if a table is available of booked
     */
    @Override
    public void updateTableStatus(int tableID, int status) {
        DBManager databaseMgr = DBManager.getDBManagerInstance();
        databaseMgr.updateTableBookingStatus(tableID,status);
    }

    @Override
    public void resetBookingStatus(Context cxt) {
        mResetBookingWatcher.setReceiver(cxt);
    }
}
