package com.example.quandoo.unittest;

import com.example.quandoo.assignment.model.CustomerResponse;
import com.example.quandoo.assignment.model.DBManager;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DBMgrTest {
    @Test
    public void testDBInsertion() {
        DBManager databaseManager = DBManager.getDBManagerInstance();
        databaseManager.insertCustomerRecord("1", "sangram", "desai");
        List<CustomerResponse> customerList = databaseManager.getCustomerNames();

        assertEquals(customerList.get(0).mCustomerFirstName, "sangram");
        assertEquals(customerList.get(0).mCustomerLastName, "desai");

    }
}
