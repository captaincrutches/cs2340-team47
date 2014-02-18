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


public class LoginPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login_page, menu);
		return true;
	}
	
	public void authenticateLogin(View view)
	{
		EditText usernameEntered = (EditText) findViewById(R.id.username);
		EditText passwordEntered = (EditText) findViewById(R.id.password);
		String username = usernameEntered.getText().toString();
		String password = passwordEntered.getText().toString();
		boolean isAdmin = isAdminUser(username, password);
		if (!isAdmin)
		{
			boolean valid = authenticateUserCredentials(username, password);
			if (valid)
			{
				Intent intent = new Intent(this, MyAccounts.class);
				startActivity(intent);
			}
			else
			{
				TextView errorMessage = (TextView) findViewById(R.id.login_failed);
				errorMessage.setTextColor(Color.RED);
			}
		}
		else
		{
			Intent intent = new Intent(this, AdminPage.class);
			startActivity(intent);
		}
	}
	
	private boolean isAdminUser(String username, String password)
	{
		// This will be replaced with getting information from the database and actually checking it
		if (username.equals("admin") && password.equals("pass123"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private boolean authenticateUserCredentials(String username, String password)
	{
		boolean valid = false;
		
		DatabaseInterfacer database = new DatabaseInterfacer(getApplicationContext());
		Cursor databaseReturn = database.getUserFromUserTable(username, password);
		databaseReturn.moveToFirst();
		int itemId = databaseReturn.getCount();
		if (itemId != 0)
		{
			valid = true;
		}
		
		
		return valid;
	}

}
