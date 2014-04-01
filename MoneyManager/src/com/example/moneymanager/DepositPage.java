package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
/**
 * @author asomani7
 */
public class DepositPage extends Activity {
    /**username of the user.*/
    private String username;
    /**name of the account.*/
    private String accountName;
    /**balance of the account.*/
    private double currentBalance;
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_page);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        accountName = bundle.getString("accountname");
        currentBalance = Double.parseDouble(bundle.getString("currentbalance"));
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deposit_page, menu);
        return true;
    }
    /**
     * @param view android requires this
     */
    public final void makeTransaction(final View view) {
        TextView transactionAmountEntered =
                (TextView)
                findViewById(R.id.account_transaction_entered);
        String amountEntered = transactionAmountEntered.getText().toString();
	boolean success = attemptTransaction(amountEntered);
	if (!success){
            showUnableToMakeTransactionErrorMessage();
        }
    }

    protected final boolean attemptTransaction(String amountEntered){
        if (!amountEntered.equals("")) {
            if (amountEntered.indexOf(".") == -1
                    ||
                    amountEntered.substring(
                            amountEntered.indexOf(".") + 1).length() <= 2) {
                double transactionAmount = Double.parseDouble(amountEntered);
                boolean successUpdate = updateBalanceInDatabase(
                        transactionAmount + currentBalance);
                boolean successAddToHistory =
                        addTransactionToAccountHistory(transactionAmount);
                if (successAddToHistory && successUpdate) {
                    transitionToAccountPage();
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /**
     * Method to show the error message when making a transaction fails.
     */
    public final void showUnableToMakeTransactionErrorMessage() {
        TextView errorMessage =
                (TextView) findViewById(R.id.transaction_failed);
        errorMessage.setTextColor(Color.RED);
    }
    /**
     * Method that updates the balance in the database.
     * @param newAccountBalance the new account balance
     * @return true if the update was successful
     */
    public boolean updateBalanceInDatabase(
            final double newAccountBalance) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        return database.updateBalance(username, accountName,
                String.valueOf(newAccountBalance));
    }
    /**
     * Method to add the transaction to account history.
     * @param transactionAmount the transaction amount
     * @return true if the transaction was added successfully to the database
     */
    public boolean addTransactionToAccountHistory(
            final double transactionAmount) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        long databaseReturn = database.addTransactionToAccountHistory("Deposit",
                username, accountName,
                String.valueOf(transactionAmount), "Deposit");
        if (databaseReturn == -1) {
            return false;
        }
        return true;
    }
    /**
     * Method to transition to the account page.
     */
    public final void transitionToAccountPage() {
        Intent intent = new Intent(this, Account.class);
        intent.putExtra("username", username);
        intent.putExtra("accountname", accountName);
        startActivity(intent);
    }

}
