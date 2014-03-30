package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * @author asomani7
 */
public class Account extends Activity {
    /** String to store the username. */
    private String username;
    /** String to store the account name. */
    private String accountName;
    /** String to store the current account balance. */
    private double currentBalance;

    /**
     * On Create method for the activity (dont questions it).
     * @param savedInstanceState
     *            default thing for android
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        accountName = bundle.getString("accountname");
        currentBalance = getBalanceFromDatabase();
        TextView accountNameText = (TextView) findViewById(R.id.account_name);
        accountNameText.setText(accountName);
        TextView balanceText = (TextView) findViewById(R.id.balance);
        balanceText.setText("$" + String.valueOf(currentBalance));
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    /**
     * @param view android makes you pass in view
     */
    public final void transitionToDepositPage(final View view) {
        Intent intent = new Intent(this, DepositPage.class);
        intent.putExtra("username", username);
        intent.putExtra("accountname", accountName);
        intent.putExtra("currentbalance", String.valueOf(currentBalance));
        startActivity(intent);
    }
    /**
     * @param view android makes you pass in view
     */
    public final void transitionToWithdrawalPage(final View view) {
        Intent intent = new Intent(this, WithdrawalPage.class);
        intent.putExtra("username", username);
        intent.putExtra("accountname", accountName);
        intent.putExtra("currentbalance", String.valueOf(currentBalance));
        startActivity(intent);
    }
    /**
     * @param view android makes you pass in view
     */
    public final void transitionToMyAccountsPage(final View view) {
        Intent intent = new Intent(this, MyAccounts.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    /**
     * @return balance of the account
     */
    public final double getBalanceFromDatabase() {
        double balance = 0;
        DatabaseInterfacer database = new DatabaseInterfacer(
                getApplicationContext());
        Cursor databaseReturn = database.getBalanceFromAccountTable(username,
                accountName);
        databaseReturn.moveToFirst();
        String stringBalance = databaseReturn.getString(databaseReturn
                .getColumnIndex("balance"));
        balance = Double.parseDouble(stringBalance);
        return balance;
    }

}
