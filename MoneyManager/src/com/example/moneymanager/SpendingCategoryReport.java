package com.example.moneymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SpendingCategoryReport extends Activity {

	private List<Map<String, String>> reportData = new ArrayList<Map<String, String>>();
	private String username;
	private String toDate;
	private String fromDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spending_category_report);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		fromDate = bundle.getString("fromDate");
		toDate = bundle.getString("toDate");
		
		Cursor databaseReturn = getDataFromDatabase(username, fromDate, toDate);
		convertCursorToMap(databaseReturn);
		
		
		SimpleAdapter adapter = new SimpleAdapter(this, reportData,
                android.R.layout.simple_list_item_2,
                new String[] {"category", "amount"},
                new int[] {android.R.id.text1,
                           android.R.id.text2});
		final ListView reportList = (ListView) findViewById(R.id.category_amount_list);
		reportList.setAdapter(adapter);
	}
	
	public void convertCursorToMap(Cursor databaseReturn)
	{
		int count = databaseReturn.getCount();
		
		Resources res = getResources();
		String[] categories = res.getStringArray(R.array.category_array);
		
		for (int i = 0; i < categories.length; i++)
		{
			databaseReturn.moveToFirst();
			Map<String, String> datum = new HashMap<String, String>(2);
			datum.put("category", categories[i]);
			double totalAmount = 0;
			
			for (int j = 0; j < count; j++)
			{
				if (databaseReturn.getString(databaseReturn.getColumnIndex("transactioncategory")).equals(categories[i]))
				{
					totalAmount += Double.parseDouble(databaseReturn.getString(databaseReturn.getColumnIndex("transactionamount")));
				}
				databaseReturn.moveToNext();
			}
			datum.put("amount", String.valueOf(totalAmount));
			reportData.add(datum);
		}
		
		
		
		
	}
	
	public void transitionToMyAccountsPage(View view)
	{
		Intent intent = new Intent(this, MyAccounts.class);
		intent.putExtra("username", username);
		startActivity(intent);
	}
	
	public Cursor getDataFromDatabase(String username, String fromDate, String toDate)
	{
		DatabaseInterfacer database = new DatabaseInterfacer(getApplicationContext());
		Cursor databaseReturn = database.getCategoryReportInformation(username, fromDate, toDate);
		return databaseReturn;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spending_category_report, menu);
		return true;
	}

}
