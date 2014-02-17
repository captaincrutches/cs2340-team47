package com.example.moneymanager;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyAccounts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_accounts);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_accounts, menu);
		return true;
	}

}
