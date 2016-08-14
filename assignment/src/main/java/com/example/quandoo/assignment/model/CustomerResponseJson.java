package com.example.quandoo.assignment.model;

import com.google.gson.annotations.SerializedName;

/**
 * The class represents Json data
 */
public class CustomerResponseJson {
    @SerializedName("customerFirstName")
    public String mcustomerFirstName;

    @SerializedName("customerLastName")
    public String mcustomerLastName;

    @SerializedName("id")
    public String mID;

    /**
     * Constructor
     * @param ID Customer ID
     * @param firstName First name of the customer
     * @param lastName last name of the customer
     */
    public CustomerResponseJson(String ID, String firstName, String lastName) {
        mcustomerFirstName = firstName;
        mcustomerLastName = lastName;
        mID = ID;
    }
}
