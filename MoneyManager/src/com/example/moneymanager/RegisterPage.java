package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterPage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_page, menu);
		return true;
	}
	
	public void registerUser(View view)
	{
		EditText usernameEntered = (EditText) findViewById(R.id.register_username_entered);
		String username = usernameEntered.getText().toString();
		EditText passwordEntered = (EditText) findViewById(R.id.register_password_entered);
		String password = passwordEntered.getText().toString();
		EditText confirmPasswordEntered = (EditText) findViewById(R.id.register_password_confirm_entered);
		String confirmPassword = confirmPasswordEntered.getText().toString();
		if (password.equals(confirmPassword))
		{
			Boolean success = insertIntoDatabase(username, password);
			
			if (success)
			{
				transitionToLoginPage();
			}
			else
			{
				showUnableToRegisterErrorMessage();
			}
		}
		else
		{
			TextView errorMessage = (TextView) findViewById(R.id.register_failed);
			errorMessage.setTextColor(Color.RED);
		}
		
		
	}
	
	public boolean insertIntoDatabase(String username, String password)
	{
		DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
		Long insertedRowId= database.insertIntoUserTable(username, password);
		
		if (insertedRowId == -1)
		{
			return false;
		}
		return true;
	}
	
	public void transitionToLoginPage()
	{
		Intent intent = new Intent(this, LoginPage.class);
		startActivity(intent);
	}
	
	public void showUnableToRegisterErrorMessage()
	{
		TextView errorMessage = (TextView) findViewById(R.id.register_failed);
		errorMessage.setTextColor(Color.RED);
	}

}
