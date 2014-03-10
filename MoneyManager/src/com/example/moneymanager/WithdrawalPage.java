package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class WithdrawalPage extends Activity {
	
	

	private String username;
	private String accountName;
	private double currentBalance;
	private String category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdrawal_page);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		accountName = bundle.getString("accountname");
		currentBalance = Double.parseDouble(bundle.getString("currentbalance"));
		
		Spinner spinner = (Spinner) findViewById(R.id.category_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        category = parent.getItemAtPosition(pos).toString();
		        
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }               
			}
		);
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
				if (transactionAmount <= currentBalance)
				{
					boolean successUpdate = updateBalanceInDatabase(currentBalance - transactionAmount);
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
		long databaseReturn = database.addTransactionToAccountHistory("Withdrawal", username, accountName, String.valueOf(transactionAmount), category);
		
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
