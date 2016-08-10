package com.example.quandoo.assignment.presenter;

import com.example.quandoo.assignment.view.TableView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

public interface TableListPresenter extends MvpPresenter<TableView>{
    public void loadTables(String url);
}
