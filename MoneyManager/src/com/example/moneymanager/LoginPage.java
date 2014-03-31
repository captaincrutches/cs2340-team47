package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author asomani7
 */
public class LoginPage extends Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_page, menu);
        return true;
    }
    /**
     * Method to authenticate the login.
     * @param view required by android
     */
    public final void authenticateLogin(final View view) {
        EditText usernameEntered = (EditText) findViewById(R.id.username);
        EditText passwordEntered = (EditText) findViewById(R.id.password);
        String username = usernameEntered.getText().toString();
        String password = passwordEntered.getText().toString();
        boolean isAdmin = Admin.isAdminUser(username, password);
        if (!isAdmin) {
            boolean valid = authenticateUserCredentials(username, password);
            if (valid) {
                Intent intent = new Intent(this, MyAccounts.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                TextView errorMessage =
                        (TextView) findViewById(R.id.login_failed);
                errorMessage.setTextColor(Color.RED);
            }
        } else {
            Intent intent = new Intent(this, AdminPage.class);
            startActivity(intent);
        }
    }
    /**
     * Method to authenticate the username and password.
     * @param username username of the user
     * @param password password of the user
     * @return true if the authentication is successful
     */
    private boolean authenticateUserCredentials(final String username,
            final String password) {
        boolean valid = false;
        DatabaseInterfacer database =
                new DatabaseInterfacer(getApplicationContext());
        Cursor databaseReturn =
                database.getUserFromUserTable(username, password);
        databaseReturn.moveToFirst();
        int itemId = databaseReturn.getCount();
        if (itemId != 0) {
            valid = true;
        }
        return valid;
    }

}
