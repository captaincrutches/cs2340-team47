package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
/**
 * @author asomani7
 */
public class RegisterPage extends Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.register_page, menu);
        return true;
    }
    /**
     * Method to register the user.
     * @param view android requires this
     */
    public final void registerUser(final View view) {
        EditText usernameEntered = (EditText)
                findViewById(R.id.register_username_entered);
        String username = usernameEntered.getText().toString();
        EditText passwordEntered = (EditText)
                findViewById(R.id.register_password_entered);
        String password = passwordEntered.getText().toString();
        EditText confirmPasswordEntered = (EditText)
                findViewById(R.id.register_password_confirm_entered);
        String confirmPassword = confirmPasswordEntered.getText().toString();
        if (password.equals(confirmPassword)
                &&
                !username.equals("") && !password.equals("")
                &&
                !confirmPassword.equals("")) {
            Boolean success = insertIntoDatabase(username, password);
            if (success) {
                transitionToLoginPage();
            } else {
                showUnableToRegisterErrorMessage();
            }
        } else {
            showUnableToRegisterErrorMessage();
        }
    }
    /**
     * Method to insert user information to the database.
     * @param username username of the user
     * @param password password of the user
     * @return true if it was added successfully
     */
    public final boolean insertIntoDatabase(
            final String username, final String password) {
        DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
        Long insertedRowId = database.insertIntoUserTable(username, password);
        if (insertedRowId == -1) {
            return false;
        }
        return true;
    }
    /**
     * Method to transition fo the login page.
     */
    public final void transitionToLoginPage() {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
    /**
     * Method to show the error message.
     */
    public final void showUnableToRegisterErrorMessage() {
        TextView errorMessage = (TextView) findViewById(R.id.register_failed);
        errorMessage.setTextColor(Color.RED);
    }

}
