package com.example.quandoo.assignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.quandoo.assignment.CustomerListAdapter;
import com.example.quandoo.assignment.model.CustomerResponse;
import com.example.quandoo.assignment.model.DBManager;
import com.example.quandoo.assignment.presenter.CustomerListPresenter;
import com.example.quandoo.assignment.presenter.SimpleCustomerListPresenter;
import com.example.quandoo.unittest.R;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import java.sql.SQLException;
import java.util.List;

/**
 * The activity to display the list of customers.
 */
public class CustomerListActivity extends MvpLceActivity<SwipeRefreshLayout, List<CustomerResponse>, CustomerView, CustomerListPresenter>
        implements CustomerView, SwipeRefreshLayout.OnRefreshListener {
    private static String urlString = "https://s3-eu-west-1.amazonaws.com/quandoo-assessment/customer-list.json";
    private static String ERROR_MSG = "Oops! could not load data. Click here to try again.";
    private CustomerListAdapter mCustomerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RecyclerView recycler_view;

        super.onCreate(savedInstanceState);
        try {
            DBManager.getDBManagerInstance().openDB(this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.customer_list);

        recycler_view = (RecyclerView) findViewById(R.id.listrecyclerView);
        mCustomerListAdapter = new CustomerListAdapter(new CustomerListAdapter.onClickListener() {
            @Override
            public void onItemClick() {
                // Since the business Logic is implemented in presenter,
                // the presenter will decide the action to be taken on this event
                presenter.takeAction();
            }
        });
        recycler_view.setAdapter(mCustomerListAdapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        loadData(false);

    }

    @Override
    public void moveToNextView() {
        Intent in = new Intent(this, TableListActivity.class);
        startActivity(in);
    }

    @Override
    public void setData(List<CustomerResponse> data) {
        mCustomerListAdapter.setData(data);
        mCustomerListAdapter.notifyDataSetChanged();
    }

    @Override
    public CustomerListPresenter createPresenter() {
        return new SimpleCustomerListPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return ERROR_MSG;
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showContent() {
        super.showContent();
        Toast.makeText(this, "please click on customer name to book the table", Toast.LENGTH_LONG).show();
    }

    /**
     * The method gets the data from the presenter
     * @param pullToRefresh true if pulled.
     */
    public void loadData(boolean pullToRefresh) {
        presenter.loadCustomers(urlString);
    }

}
