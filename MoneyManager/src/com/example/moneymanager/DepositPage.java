package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DepositPage extends Activity {

	private String username;
	private String accountName;
	private double currentBalance;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deposit_page);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		accountName = bundle.getString("accountname");
		currentBalance = Double.parseDouble(bundle.getString("currentbalance"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deposit_page, menu);
		return true;
	}

	public void makeTransaction(View view)
	{
		TextView transactionAmountEntered = (TextView)findViewById(R.id.account_transaction_entered);
		String amountEntered = transactionAmountEntered.getText().toString();
		
		
		if (!amountEntered.equals(""))
		{
			if (amountEntered.indexOf(".") == -1 || amountEntered.substring(amountEntered.indexOf(".") + 1).length() <= 2)
			{
				double transactionAmount = Double.parseDouble(amountEntered);
				boolean successUpdate = updateBalanceInDatabase(transactionAmount + currentBalance);
				boolean successAddToHistory = addTransactionToAccountHistory(transactionAmount);
				
				if (successAddToHistory && successUpdate)
				{
					transitionToAccountPage();
				}
				else
				{
					showUnableToMakeTransactionErrorMessage();
				}
	
			}
			else
			{
				showUnableToMakeTransactionErrorMessage();
			}
		}
		else
		{
			showUnableToMakeTransactionErrorMessage();
		}

	}
	
	public void showUnableToMakeTransactionErrorMessage()
	{
		TextView errorMessage = (TextView) findViewById(R.id.transaction_failed);
		errorMessage.setTextColor(Color.RED);
	}
	
	public boolean updateBalanceInDatabase(double newAccountBalance)
	{
		DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
		return database.updateBalance(username, accountName, String.valueOf(newAccountBalance));
		
	}
	
	public boolean addTransactionToAccountHistory(double transactionAmount)
	{
		DatabaseInterfacer database = new DatabaseInterfacer(getBaseContext());
		long databaseReturn = database.addTransactionToAccountHistory("Deposit", username, accountName, String.valueOf(transactionAmount), "Deposit");
		
		if (databaseReturn == -1)
		{
			return false;
		}
		return true;
	}
	
	public void transitionToAccountPage()
	{
		Intent intent = new Intent(this, Account.class);
		intent.putExtra("username", username);
		intent.putExtra("accountname", accountName);
		startActivity(intent);
	}

}
