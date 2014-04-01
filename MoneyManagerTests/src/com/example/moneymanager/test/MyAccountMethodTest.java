package com.example.moneymanager.test;

import java.util.Random;

import com.example.moneymanager.DatabaseInterfacer;
import com.example.moneymanager.MyAccounts;

import android.database.Cursor;
import android.test.ActivityUnitTestCase;

/**
 * Class to test the method convertCursorToArray in MyAccounts.java
 * 
 * @author Matt Schmidt
 */
public class MyAccountMethodTest extends ActivityUnitTestCase<MyAccounts> {
	/** Holds the number of users to test with */
	private int numUsers;
	/** Each user has a certain number of accounts */
	private int[] numAccounts;
	/** Object to use for database queries */
	private DatabaseInterfacer db;
	
	/**
	 * Constructor required by Android testing framework
	 */
	public MyAccountMethodTest() {
		super(MyAccounts.class);
	}
	
	@Override
	protected void setUp() {
		db = new DatabaseInterfacer(getInstrumentation().getTargetContext());
		Random r = new Random();
		
		//Populate the database with some users
		numUsers = 1 + r.nextInt(15);	//Start with 1-15 users
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
			numAccounts[i] = r.nextInt(11);	//Everyone has 0-10 accounts
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
	
	public void testArraySize() {
		//Run on each user
		for(int i = 0; i < numUsers; i++) {
			String username = "user" + i;
			
			//Create an object we can use
			Cursor cursor = db.getAccountFromAccountTable(username);
			//We don't need to create an activity to do this :D
			String[] array = new MyAccounts().convertCursorToArray(cursor);
			
			//And make sure we get the right amount of data
			assertEquals(username + " returned an array of incorrect size", numAccounts[i], array.length);
		}
	}
	
	public void testArrayContents() {
		//Same idea as the other method
		for(int i = 0; i < numUsers; i++) {
			String username = "user" + i;
			
			//Create an object we can use
			Cursor cursor = db.getAccountFromAccountTable(username);
			//We don't need to create an activity to do this :D
			String[] array = new MyAccounts().convertCursorToArray(cursor);
			
			//Except now we test the data itself
			for(int j = 0; j < numAccounts[i]; j++) {
				String accountName = "account" + i + "-" + j;
				try {
					assertEquals("Account name mismatch", accountName, array[j]);
				} catch(ArrayIndexOutOfBoundsException e) {
					fail("Array index where " + accountName + " should be doesn't exist!");
				}
			}
		}
	}
	
	protected void tearDown() {
		//Delete all the account data, to avoid failures on consecutive runs
		db.getWritableDatabase().delete(DatabaseInterfacer.FeedEntry.ACCOUNT_TABLE_NAME, null, null);
	}
}
