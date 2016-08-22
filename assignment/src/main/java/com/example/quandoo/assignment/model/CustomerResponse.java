package com.example.quandoo.assignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * The class represents Json data
 */
public class CustomerResponse {
    @SerializedName("customerFirstName")
    public String mCustomerFirstName;

    @SerializedName("customerLastName")
    public String mCustomerLastName;

    @SerializedName("id")
    public String mID;

    /**
     * Constructor
     * @param ID Customer ID
     * @param firstName First name of the customer
     * @param lastName last name of the customer
     */
    public CustomerResponse(String ID, String firstName, String lastName) {
        mCustomerFirstName = firstName;
        mCustomerLastName = lastName;
        mID = ID;
    }
}
