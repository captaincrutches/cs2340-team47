package com.example.moneymanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MyAccounts extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_accounts);
		
		Bundle bundle = getIntent().getExtras();
		String username = bundle.getString("username");
		
		Cursor databaseReturn = getAccountsFromDatabase(username);
		String[] accountList = convertCursorToArray(databaseReturn);
		
		//String[] accountList = {"one", "two"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
		        android.R.layout.simple_list_item_1, accountList);
		
		ListView listView = (ListView) findViewById(R.id.accounts_list);
		listView.setAdapter(adapter);
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
