package com.example.quandoo.assignment.view;

import com.example.quandoo.assignment.model.CustomerResponse;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * The view interface as per MOSBY MVP design.
 * http://hannesdorfmann.com/mosby/mvp/
 */
public interface CustomerView extends MvpLceView<List<CustomerResponse>> {

    public void moveToNextView();
}
