package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
/**
 * @author asomani7
 */
public class MyAccounts extends Activity {
    /**Username of the user.*/
    private String username;
    /**Account that was selected from the page.*/
    private String accountSelected;
    /**The list of all the accounts.*/
    private String[] accountList;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_accounts);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        Cursor databaseReturn = getAccountsFromDatabase(username);
        accountList = convertCursorToArray(databaseReturn);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, accountList);
        final ListView listView = (ListView) findViewById(R.id.accounts_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
              public void onItemClick(final AdapterView<?> myAdapter,
                      final View myView, final int myItemInt,
                      final long mylng) {
                accountSelected =
                        (String) (listView.getItemAtPosition(myItemInt));
                transitionToAccountPage();
              }
        });
    }
    /**
     * Method to transition to the account page.
     */
    public final void transitionToAccountPage() {
        Intent intent = new Intent(this, Account.class);
        intent.putExtra("username", username);
        intent.putExtra("accountname", accountSelected);
        startActivity(intent);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_accounts, menu);
        return true;
    }
    /**
     * Method to go to the accounts page.
     * @param view android requires this
     */
    public final void goToCreateAccountsPage(final View view) {
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("username");
        Intent intent = new Intent(this, CreateAccount.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
    /**
     * Method to go to the reports page.
     * @param view android requires this
     */
    public final void goToReportsPage(final View view) {
        Bundle bundle = getIntent().getExtras();
        String user = bundle.getString("username");
        Intent intent = new Intent(this, Reports.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
    /**
     * Method to get accounts from the database.
     * @param user username of the user
     * @return Cursor about that contains the data from the database
     */
    public final Cursor getAccountsFromDatabase(final String user) {
        DatabaseInterfacer database =
                new DatabaseInterfacer(getApplicationContext());
        Cursor databaseReturn = database.getAccountFromAccountTable(user);
        return databaseReturn;
    }
    /**
     * Method to convert the Cursor about to an array.
     * @param databaseReturn the cursor object with database information
     * @return Array of the accounts
     */
    public final String[] convertCursorToArray(final Cursor databaseReturn) {
        databaseReturn.moveToFirst();
        int count = databaseReturn.getCount();
        String[] convertedCursor = new String[count];
        for (int i = 0; i < count; i++) {
            convertedCursor[i] =
                    databaseReturn.getString(
                           databaseReturn.getColumnIndex("accountname"));
            databaseReturn.moveToNext();
        }
        return convertedCursor;
    }
}
