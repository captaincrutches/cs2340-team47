package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
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
		EditText username = (EditText) findViewById(R.id.username);
		EditText password = (EditText) findViewById(R.id.password);
		boolean valid = doLoginCredentialsMatch(username, password);
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
	
	private boolean doLoginCredentialsMatch(EditText username, EditText password)
	{
		// This will be replaced with getting information from the database and actually checking it
		if (username.getText().toString().equals("admin") && password.getText().toString().equals("password"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
