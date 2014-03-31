package com.example.moneymanager;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
/**
 * @author asomani7
 */
public class AdminUserTests {

    /**
     * Should return false.
     */
    @Test
    public final void testIsAdminUser() {
        String testUsername = "a";
        String testPassword = "a";
        Boolean result = Admin.isAdminUser(testUsername, testPassword);
        //Boolean result = LoginPage.isAdminUser(testUsername, testPassword);
        assertFalse("Testing with username = a and password = b."
                +
                "Should return false", result);
    }

}
