package com.example.quandoo.assignment.view;

import android.content.Intent;

import com.example.quandoo.assignment.model.CustomerResponseJson;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * The view interface as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 */
public interface CustomerView extends MvpLceView<List<CustomerResponseJson>> {

    public void moveToNextView();
}
