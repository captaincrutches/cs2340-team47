package com.example.moneymanager;

import static org.junit.Assert.*;


import com.example.moneymanager.DatabaseInterfacer;


// import org.junit.After;

import org.junit.Test;
/*
 * Class testing CreateAccount.insertIntoDatabase method
 * 
 * @author Victoria Palacios
 */
public class CreateAccountTest extends CreateAccount {
//	private DatabaseInterfacer db;
//	private final String accountName = "accountName";
//	private final String userName = "username";
	private final CreateAccount ca = new CreateAccount();
	
	/**
	 * Constructor required by Android testing framework
	 * @return 
	 */
	public CreateAccountTest() {
		super();
	}
	
/*	protected void setUp() {
		//db = new DatabaseInterfacer(getInstrumentation().getTargetContext());
		
		long row = db.insertIntoUserTable(userName, "password");
		if(row == -1) {
			System.out.println("Error inserting u into db");
		}
		
		row = db.insertIntoAccountsTable(accountName, "10.00", userName);
		if(row == -1) {
			System.out.println("Error inserting " + accountName + " into db");
		}
	}
*/
    /**
     * Should return false.
     */
    @Test
    public final void testInvalidAccountName() {
        assertFalse("Attempting to use invalid account name."
                +
                "Should return false", ca.insertIntoDatabase(null, "00.00", "username"));
    }

    @Test
    public final void testInvalidBalance() {
        assertFalse("Attempting to use invalid balane."
                +
                "Should return false", ca.insertIntoDatabase("accountName", null, "username"));
    }
    

    @Test
    public final void testInvalidUsername() {
        assertFalse("Attempting to use invalid username."
                +
                "Should return false", ca.insertIntoDatabase("accountName", "00.00", null));
    }    
    
    /**
     * Should return true.
     */
    @Test
    public final void testValid() {
        assertTrue("Attempting to use valid account name, balance, and username."
                +
                "Should return true", ca.insertIntoDatabase("accountName", "00.00", "username"));
    }
    
    
	/*@After
	public void tearDown() throws Exception {
		//Delete all the account data, to avoid failures on consecutive runs
		db.getWritableDatabase().delete(DatabaseInterfacer.FeedEntry.ACCOUNT_TABLE_NAME, null, null);
	
	}*/


}
