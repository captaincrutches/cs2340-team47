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
public class CreateAccount extends Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_account, menu);
        return true;
    }
    /**
     * @param view android requires this
     */
    public final void createNewAccount(final View view) {
        TextView accountNameEntered =
                (TextView)
                 findViewById(R.id.account_account_name_entered);
        TextView balanceEntered =
                (TextView)
                findViewById(R.id.account_balance_entered);
        String accountName = accountNameEntered.getText().toString();
        String balance = balanceEntered.getText().toString();
        if (!accountName.equals("") && !balance.equals("")) {
            Bundle bundle = getIntent().getExtras();
            String username = bundle.getString("username");
            Boolean success =
                    insertIntoDatabase(accountName, balance, username);
            if (success) {
                transitionToAccountsPage();
            } else {
                showUnableToCreateAccountErrorMessage();
            }
        } else {
            showUnableToCreateAccountErrorMessage();
        }
    }
    /**
     * @param accountName name of account
     * @param balance balance of account
     * @param username username of the user
     * @return true if the insert was successful
     */
    public final boolean insertIntoDatabase(final String accountName,
            final String balance, final String username) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        Long insertedRowId =
                database.insertIntoAccountsTable(accountName,
                        balance, username);
        if (insertedRowId == -1) {
            return false;
        }
        return true;
    }
    /**
     * method to transition to the accounts page.
     */
    public final void transitionToAccountsPage() {
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        Intent intent = new Intent(this, MyAccounts.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    /**
     * Method to show the error message.
     */
    public final void showUnableToCreateAccountErrorMessage() {
        TextView errorMessage = (TextView)
                findViewById(R.id.account_create_failed);
        errorMessage.setTextColor(Color.RED);
    }

}
