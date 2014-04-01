package com.example.moneymanager;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the attemptTransaction method in DepositPage.java
 * @author Alexander Ulan Hernandez
 */
public class DepositPageTester extends DepositPage{


    /**
     * Should return false.
     */
    @Test
    public final void testNoInput() {
        assertFalse("Attempting to deposit a blank value."
                +
                "Should return false", attemptTransaction(""));
    }


    /**
     * Should return false.
     */
    @Test
    public final void testInvalidCents() {
        assertFalse("Attempting to deposit an absurdly large number of cents."
                +
                "Should return false", attemptTransaction("5.55555555"));
    }


    /**
     * Should return true.
     */
    @Test
    public final void testValid() {
        assertTrue("Attempting to make a reasonable deposit."
                +
                "Should return true",attemptTransaction("5.34"));
    }

    @Override
    public final boolean addTransactionToAccountHistory(final double transactionAmount) {
        return true;
    }

    @Override
    public final boolean updateBalanceInDatabase(final double transactionAmount) {
        return true;
    }

}
