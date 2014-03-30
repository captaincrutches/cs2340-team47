package com.example.moneymanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * @author asomani7
 */
public class AdminPage extends Activity {
    /**
     * @param savedInstanceState android requires this
     */
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    /**
     * @param menu android requires this
     * @return true if the create was successful
     */
    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_page, menu);
        return true;
    }

}
