package com.example.moneymanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseInterfacer extends SQLiteOpenHelper
{
    public static abstract class FeedEntry implements BaseColumns 
    {
    	public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String ACCOUNT_TABLE_NAME = "accounts";
        public static final String COLUMN_NAME_ACCOUNT_NAME = "accountname";
        public static final String COLUMN_NAME_ACCOUNT_BALANCE = "balance";
        public static final String COLUMN_NAME_TRANSACTION_AMOUNT = "transactionamount";
        public static final String COLUMN_NAME_DATE_TIME = "datetime";
        public static final String TRANSACTION_TABLE_NAME = "transactions";
        public static final String COLUMN_NAME_TRANSACTION_TYPE = "transactiontype";
        
    }
    
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "MoneyManager.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
        FeedEntry._ID + " INTEGER PRIMARY KEY," +
        FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
        FeedEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE +
        " );";
    
    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + FeedEntry.ACCOUNT_TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_ACCOUNT_BALANCE + TEXT_TYPE + 
            " );";
    
    private static final String SQL_CREATE_TRANSACTION =
            "CREATE TABLE " + FeedEntry.TRANSACTION_TABLE_NAME + " (" +
            FeedEntry._ID + " INTEGER PRIMARY KEY," +
            FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_TRANSACTION_TYPE + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_TRANSACTION_AMOUNT + TEXT_TYPE + COMMA_SEP +
            FeedEntry.COLUMN_NAME_DATE_TIME + DATETIME_TYPE + " DEFAULT CURRENT_TIMESTAMP" + 
            " );";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    
    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + FeedEntry.ACCOUNT_TABLE_NAME;
    
    private static final String SQL_DELETE_TRANSACTION =
            "DROP TABLE IF EXISTS " + FeedEntry.TRANSACTION_TABLE_NAME;
	//END OF VARIABLE CREATION. START CLASS METHODS
    
	public DatabaseInterfacer(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) 
	{
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ACCOUNT);
        db.execSQL(SQL_CREATE_TRANSACTION);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ACCOUNT);
        db.execSQL(SQL_DELETE_TRANSACTION);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        onUpgrade(db, oldVersion, newVersion);
    }
    
    public long insertIntoUserTable(String username, String password)
    {

	    SQLiteDatabase db = this.getWritableDatabase();
	
	    ContentValues values = new ContentValues();
	    values.put(FeedEntry.COLUMN_NAME_USERNAME, username);
	    values.put(FeedEntry.COLUMN_NAME_PASSWORD, password);
	
	    long newRowId;
	    newRowId = db.insert(
	             FeedEntry.TABLE_NAME,
	             null,
	             values);
	    
	    return newRowId;
    }
    
    public Cursor getUserFromUserTable (String username, String password)
    {
    	SQLiteDatabase db = this.getReadableDatabase();

    	
    	Cursor test = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username, password});
    	return test;
    	
    }
    
    public long insertIntoAccountsTable(String accountName, String balance, String username)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
	    ContentValues values = new ContentValues();
	    values.put(FeedEntry.COLUMN_NAME_USERNAME, username);
	    values.put(FeedEntry.COLUMN_NAME_ACCOUNT_NAME, accountName);
	    values.put(FeedEntry.COLUMN_NAME_ACCOUNT_BALANCE, balance);
	
	    long newRowId;
	    newRowId = db.insert(
	             FeedEntry.ACCOUNT_TABLE_NAME,
	             null,
	             values);
	    
	    return newRowId;
    }
    
    public Cursor getAccountFromAccountTable (String username)
    {
    	SQLiteDatabase db = this.getReadableDatabase();

    	
    	Cursor test = db.rawQuery("SELECT * FROM accounts WHERE username = ?" , new String[] {username});
    	return test;
    	
    }
    
    public Cursor getBalanceFromAccountTable(String username, String accountName)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor test = db.rawQuery("SELECT * FROM accounts where username = ? AND accountname = ?" , new String[] {username, accountName});
    	return test;
    }
    
    public boolean updateBalance(String username, String accountName, String newAccountBalance)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	db.execSQL("UPDATE accounts SET balance = ? WHERE username = ? AND accountname = ?" , new String[] {newAccountBalance, username, accountName});
    	return true;
    }
    
    public long addTransactionToAccountHistory(String transactionType, String username, String accountName, String transactionAmount)
    {
    	/*
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String dateTime = dateFormat.format(cal.getTime()).toString();
		*/
    	SQLiteDatabase db = this.getWritableDatabase();
    	
	    ContentValues values = new ContentValues();
	    values.put(FeedEntry.COLUMN_NAME_USERNAME, username);
	    values.put(FeedEntry.COLUMN_NAME_ACCOUNT_NAME, accountName);
	    values.put(FeedEntry.COLUMN_NAME_TRANSACTION_AMOUNT, transactionAmount);
	    values.put(FeedEntry.COLUMN_NAME_TRANSACTION_TYPE, transactionType);
	    values.put(FeedEntry.COLUMN_NAME_DATE_TIME, getDateTime());
	
	    long newRowId;
	    newRowId = db.insert(
	             FeedEntry.TRANSACTION_TABLE_NAME,
	             null,
	             values);
	    
	    return newRowId;
    }
    
    private String getDateTime() 
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
