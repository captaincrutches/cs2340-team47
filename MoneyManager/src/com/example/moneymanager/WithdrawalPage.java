package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * @author asomani7
 */
public class WithdrawalPage extends Activity {
    /**Username of the user.*/
    private String username;
    /**Name of the account.*/
    private String accountName;
    /**Balance of the account.*/
    private double currentBalance;
    /**Category of the withdrawal.*/
    private String category;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_page);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        accountName = bundle.getString("accountname");
        currentBalance = Double.parseDouble(bundle.getString("currentbalance"));
        Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.category_array,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> parent,
                    final View view,
                    final int pos, final long id) {
                category = parent.getItemAtPosition(pos).toString();
            }

            public void onNothingSelected(final AdapterView<?> parent) {
                // Another interface callback
            }
            }
        );
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deposit_page, menu);
        return true;
    }
    /**
     * Method to make the transaction.
     * @param view android requires this
     */
    public final void makeTransaction(final View view) {
        TextView transactionAmountEntered =
                (TextView) findViewById(R.id.account_transaction_entered);
        String amountEntered = transactionAmountEntered.getText().toString();
        if (!amountEntered.equals("")) {
            if (amountEntered.indexOf(".") == -1 || amountEntered.substring(
                    amountEntered.indexOf(".") + 1).length() <= 2) {
                double transactionAmount = Double.parseDouble(amountEntered);
                if (transactionAmount <= currentBalance) {
                    boolean successUpdate = updateBalanceInDatabase(
                            currentBalance - transactionAmount);
                    boolean successAddToHistory =
                            addTransactionToAccountHistory(transactionAmount);
                    if (successAddToHistory && successUpdate) {
                        transitionToAccountPage();
                    } else {
                        showUnableToMakeTransactionErrorMessage();
                    }
                } else {
                    showUnableToMakeTransactionErrorMessage();
                }
            } else {
                showUnableToMakeTransactionErrorMessage();
            }

        } else {
            showUnableToMakeTransactionErrorMessage();
        }

    }
    /**
     * Method to show the error message.
     */
    public final void showUnableToMakeTransactionErrorMessage() {
        TextView errorMessage =
                (TextView) findViewById(R.id.transaction_failed);
        errorMessage.setTextColor(Color.RED);
    }
    /**
     * Method to update the balance in the database.
     * @param newAccountBalance the new balance
     * @return true if the update was successful
     */
    public final boolean updateBalanceInDatabase(
            final double newAccountBalance) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        return database.updateBalance(username,
                accountName, String.valueOf(newAccountBalance));

    }
    /**
     * Method to add the transaction to the account history.
     * @param transactionAmount the transaction amount
     * @return true if the add was successful
     */
    public final boolean addTransactionToAccountHistory(
            final double transactionAmount) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        long databaseReturn = database.addTransactionToAccountHistory(
                "Withdrawal", username, accountName,
                      String.valueOf(transactionAmount), category);
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
