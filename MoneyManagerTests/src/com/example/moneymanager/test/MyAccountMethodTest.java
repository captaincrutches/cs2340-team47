package com.example.moneymanager.test;

import java.util.Random;

import com.example.moneymanager.DatabaseInterfacer;
import com.example.moneymanager.MyAccounts;

import android.content.Intent;
import android.database.Cursor;
import android.test.ActivityUnitTestCase;

public class MyAccountMethodTest extends ActivityUnitTestCase<MyAccounts> {
	private int numUsers = 10;
	private int[] numAccounts;
	
	public MyAccountMethodTest() {
		super(MyAccounts.class);
	}
		
	protected void setUp() {        
		setActivityContext(getInstrumentation().getTargetContext());
        DatabaseInterfacer db = new DatabaseInterfacer(getInstrumentation().getTargetContext());
		Random r = new Random();
		
		//Populate the database with some users
		numAccounts = new int[numUsers];
		for(int i = 0; i < numUsers; i++) {
			String username = "user" + i;
			
			//Insert the user
			long row = db.insertIntoUserTable(username, "password" + i);
			if(row == -1) {
				System.out.println("Error inserting " + username + " into db");
				break;
			}
			
			//Now give the user some accounts
			numAccounts[i] = r.nextInt(10);
			for(int j = 0; j < numAccounts[i]; j++) {
				String accountName = "account" + i + "-" + j;
				row = db.insertIntoAccountsTable(accountName, j + ".00", username);
				if(row == -1) {
					System.out.println("Error inserting " + accountName + " into db");
					break;
				}
			}
		}
	}
	
	public void testTesting() {
		//Run on each user
		for(int i = 0; i < numUsers; i++) {
			//Create an object we can use
			Intent intent = new Intent(getInstrumentation().getTargetContext(), MyAccounts.class);
			intent.putExtra("username", "user" + i);
			startActivity(intent, null, null);
			
			Cursor cursor = getActivity().getAccountsFromDatabase("user" + i);
			String[] array = getActivity().convertCursorToArray(cursor);
			assertEquals("Method returned an array of incorrect size", numAccounts[i], array.length);
		}
	}
}
