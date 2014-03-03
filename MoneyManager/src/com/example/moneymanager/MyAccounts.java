package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAccounts extends Activity {
	
	private String username;
	private String accountSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_accounts);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		
		Cursor databaseReturn = getAccountsFromDatabase(username);
		String[] accountList = convertCursorToArray(databaseReturn);
	
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, accountList);
		
		final ListView listView = (ListView) findViewById(R.id.accounts_list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
		      public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
		        accountSelected =(String) (listView.getItemAtPosition(myItemInt));
		        transitionToAccountPage();
		      }                 
		});
	}
	
	public void transitionToAccountPage()
	{
		Intent intent = new Intent(this, Account.class);
		intent.putExtra("username", username);
		intent.putExtra("accountname", accountSelected);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_accounts, menu);
		return true;
	}
	
	public void goToCreateAccountsPage(View view)
	{
		Bundle bundle = getIntent().getExtras();
		String username = bundle.getString("username");
		Intent intent = new Intent(this, CreateAccount.class);
		
		intent.putExtra("username", username);
		startActivity(intent);
	}

	public Cursor getAccountsFromDatabase(String username)
	{
		DatabaseInterfacer database = new DatabaseInterfacer(getApplicationContext());
		Cursor databaseReturn = database.getAccountFromAccountTable(username);
		return databaseReturn;
	}
	
	public String[] convertCursorToArray(Cursor databaseReturn)
	{
		databaseReturn.moveToFirst();
		int count = databaseReturn.getCount();
		String[] convertedCursor = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			convertedCursor[i] = databaseReturn.getString(databaseReturn.getColumnIndex("accountname"));
			databaseReturn.moveToNext();
		}
		
		return convertedCursor;
	}
}
