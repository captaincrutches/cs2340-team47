package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Account extends Activity {
	
	private String username;
	private String accountName;
	private double currentBalance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		accountName = bundle.getString("accountname");
		currentBalance = getBalanceFromDatabase();
		
		TextView accountNameText = (TextView) findViewById(R.id.account_name);
		accountNameText.setText(accountName);
		
		TextView balanceText = (TextView) findViewById(R.id.balance);
		balanceText.setText("$" + String.valueOf(currentBalance));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}
	
	public void transitionToDepositPage(View view)
	{
		Intent intent = new Intent(this, DepositPage.class);
		intent.putExtra("username", username);
		intent.putExtra("accountname", accountName);
		intent.putExtra("currentbalance", String.valueOf(currentBalance));
		startActivity(intent);
	}
	
	public void transitionToWithdrawalPage(View view)
	{
		Intent intent = new Intent(this, WithdrawalPage.class);
		intent.putExtra("username", username);
		intent.putExtra("accountname", accountName);
		intent.putExtra("currentbalance", String.valueOf(currentBalance));
		startActivity(intent);
	}
	
	public void transitionToMyAccountsPage(View view)
	{
		Intent intent = new Intent(this, MyAccounts.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public double getBalanceFromDatabase()
	{
		double balance = 0;
		DatabaseInterfacer database = new DatabaseInterfacer(getApplicationContext());
		Cursor databaseReturn = database.getBalanceFromAccountTable(username, accountName);
		databaseReturn.moveToFirst();
		String stringBalance = databaseReturn.getString(databaseReturn.getColumnIndex("balance"));
		balance = Double.parseDouble(stringBalance);
		return balance;
	}

}
