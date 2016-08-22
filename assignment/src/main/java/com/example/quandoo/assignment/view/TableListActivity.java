package com.example.quandoo.assignment.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.quandoo.assignment.ResetBookingWatcher;
import com.example.quandoo.assignment.TableListAdapter;
import com.example.quandoo.assignment.model.DBManager;
import com.example.quandoo.assignment.presenter.SimpleTableListPresenter;
import com.example.quandoo.assignment.presenter.TableListPresenter;
import com.example.quandoo.unittest.R;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.util.Calendar;
import java.util.List;

/**
 * The activity to display the table list screen. The tables are displayed in grid format
 * with red color denotes the already reserved table and green being the available one.
 * The blue color denotes the table reserved by the user.
 */
public class TableListActivity extends MvpLceActivity<SwipeRefreshLayout, List<Integer>, TableView, TableListPresenter>
        implements TableView, SwipeRefreshLayout.OnRefreshListener{
    private static String TABLE_STRING = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/table-map.json";
    private static String ERROR_MSG = "Oops! could not load data. Click here to try again.";
    public static final String INTENT_RESET_BOOKING = "intent_reset_booking";
    private static int COLUMNS = 3;
    private RecyclerView mRecyclerView;
    private List<Integer> mTableBookingList;
    private TableListAdapter mTableListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookingtable_grid);

        mRecyclerView = (RecyclerView) findViewById(R.id.tableRecyclerView);
        mTableListAdapter = new TableListAdapter(new TableListAdapter.onClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(), "Table booked successfully!!", Toast.LENGTH_SHORT).show();
                mTableBookingList.set(position, DBManager.TABLE_RESERVED);
                mTableListAdapter.notifyDataSetChanged();

                presenter.updateTableStatus(position, DBManager.TABLE_RESERVED);
            }
        });
        mRecyclerView.setAdapter(mTableListAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, COLUMNS));

        loadData(false);

        presenter.resetBookingStatus(getApplicationContext());
        setReservationResetAlarm();
    }

    @Override
    public void setData(List<Integer> data) {
        mTableBookingList = data;
        mTableListAdapter.setData(data);
        mTableListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {
        super.showContent();
        Toast.makeText(this, "Tables marked with green are available", Toast.LENGTH_LONG).show();
    }

    @Override
    public TableListPresenter createPresenter() {
        return new SimpleTableListPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return ERROR_MSG;
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    /**
     * The method gets the data from the presenter
     * @param pullToRefresh
     */
    public void loadData(boolean pullToRefresh) {
        presenter.loadTables(TABLE_STRING);
    }

    public void setReservationResetAlarm() {
        Intent in = new Intent(this, ResetBookingWatcher.bookingReceiver.class);
        in.setAction(INTENT_RESET_BOOKING);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, in, 0);
        AlarmManager alarm_Mgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarm_Mgr.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(), 60000, pendingIntent);
    }
}
