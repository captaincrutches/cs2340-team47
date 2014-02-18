package com.example.moneymanager;

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
    }
    
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoneyManager.db";
    
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
        FeedEntry._ID + " INTEGER PRIMARY KEY," +
        FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP +
        FeedEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE +
        " );";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
	//END OF VARIABLE CREATION. START CLASS METHODS
    
	public DatabaseInterfacer(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) 
	{
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        db.execSQL(SQL_DELETE_ENTRIES);
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
}
