package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
/**
 * @author asomani7
 */
public class MainActivity extends Activity {

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * Method to transition to the login screen.
     * @param view android requires this
     */
    public final void goToLoginScreen(final View view) {
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);
    }
    /**
     * Method to go to the register page.
     * @param view android requires this
     */
    public final void goToRegisterScreen(final View view) {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }

}
