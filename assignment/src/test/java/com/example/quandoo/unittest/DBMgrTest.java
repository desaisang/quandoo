package com.example.quandoo.unittest;

import com.example.quandoo.assignment.model.CustomerResponseJson;
import com.example.quandoo.assignment.model.DBManager;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DBMgrTest {
    @Test
    public void testDBInsertion() {
        DBManager databaseManager = DBManager.getDBManagerInstance();
        databaseManager.insertCustomerRecord("1", "sangram", "desai");
        List<CustomerResponseJson> customerList = databaseManager.getCustomerNames();

        assertEquals(customerList.get(0).mcustomerFirstName, "sangram");
        assertEquals(customerList.get(0).mcustomerLastName, "desai");

    }
}
