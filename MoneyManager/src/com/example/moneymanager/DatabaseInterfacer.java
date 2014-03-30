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
/**
 * @author asomani7
 */
public class DatabaseInterfacer extends SQLiteOpenHelper {
    /**
     * @author asomani7
     */
    public abstract static class FeedEntry implements BaseColumns {
        /**String to hold the user table name string.*/
        public static final String TABLE_NAME = "users";
        /**String to hold the username column string.*/
        public static final String COLUMN_NAME_USERNAME = "username";
        /**String to hold the password column string.*/
        public static final String COLUMN_NAME_PASSWORD = "password";
        /**String to hold the account table name string.*/
        public static final String ACCOUNT_TABLE_NAME = "accounts";
        /**String to hold the account name column string.*/
        public static final String COLUMN_NAME_ACCOUNT_NAME = "accountname";
        /**String to hold the user balance column string.*/
        public static final String COLUMN_NAME_ACCOUNT_BALANCE = "balance";
        /**String to hold the transaction amount string.*/
        public static final String COLUMN_NAME_TRANSACTION_AMOUNT =
                "transactionamount";
        /**String to hold the date time string.*/
        public static final String COLUMN_NAME_DATE_TIME = "timeoftransaction";
        /**String to hold the transactions table name.*/
        public static final String TRANSACTION_TABLE_NAME = "transactions";
        /**String to hold the transaction type column string.*/
        public static final String COLUMN_NAME_TRANSACTION_TYPE =
                "transactiontype";
        /**String to hold the transaction category column string.*/
        public static final String COLUMN_NAME_TRANSACTION_CATEGORY =
                "transactioncategory";
    }
    /**String to hold the database version number.*/
    public static final int DATABASE_VERSION = 11;
    /**String to hold the database name.*/
    public static final String DATABASE_NAME = "MoneyManager.db";
    /**String to hold the string text.*/
    private static final String TEXT_TYPE = " TEXT";
    /**String to hold the string datetime.*/
    private static final String DATETIME_TYPE = " DATETIME";
    /**String to hold the string ,.*/
    private static final String COMMA_SEP = ",";
    /**String to hold the SQL statement to create entries.*/
    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedEntry.TABLE_NAME + " ("
        +
        FeedEntry._ID + " INTEGER PRIMARY KEY,"
        +
        FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP
        +
        FeedEntry.COLUMN_NAME_PASSWORD + TEXT_TYPE
        +
        " );";
    /**String to hold the SQL statement to create the account table.*/
    private static final String SQL_CREATE_ACCOUNT =
            "CREATE TABLE " + FeedEntry.ACCOUNT_TABLE_NAME + " ("
            +
            FeedEntry._ID + " INTEGER PRIMARY KEY,"
            +
            FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_ACCOUNT_BALANCE + TEXT_TYPE
            +
            " );";
    /**String to hold the SQL statement to create the transaction table.*/
    private static final String SQL_CREATE_TRANSACTION =
            "CREATE TABLE " + FeedEntry.TRANSACTION_TABLE_NAME + " ("
            +
            FeedEntry._ID + " INTEGER PRIMARY KEY,"
            +
            FeedEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_ACCOUNT_NAME + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_TRANSACTION_TYPE + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_TRANSACTION_AMOUNT + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_TRANSACTION_CATEGORY + TEXT_TYPE + COMMA_SEP
            +
            FeedEntry.COLUMN_NAME_DATE_TIME + DATETIME_TYPE
            +
            " DEFAULT CURRENT_TIMESTAMP"
            +
            " );";
    /**String to hold the SQL statement to delete user table.*/
    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    /**String to hold the SQL statement to delete the account table.*/
    private static final String SQL_DELETE_ACCOUNT =
            "DROP TABLE IF EXISTS " + FeedEntry.ACCOUNT_TABLE_NAME;
    /**String to hold the SQL statement to delete the transaction table.*/
    private static final String SQL_DELETE_TRANSACTION =
            "DROP TABLE IF EXISTS " + FeedEntry.TRANSACTION_TABLE_NAME;
    //END OF VARIABLE CREATION. START CLASS METHODS
    /**
     * @param context required for SQLite database
     */
    public DatabaseInterfacer(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    /**
     * @param db reference to the database
     */
    public final void onCreate(final SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ACCOUNT);
        db.execSQL(SQL_CREATE_TRANSACTION);
    }
    /**
     * @param db reference to the database
     * @param oldVersion version number of the old database
     * @param newVersion version number of the new database
     */
    public final void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ACCOUNT);
        db.execSQL(SQL_DELETE_TRANSACTION);
        onCreate(db);
    }
    /**
     * @param db reference to the database
     * @param oldVersion version number of the old database
     * @param newVersion version number of the new database
     */
    public final void onDowngrade(final SQLiteDatabase db,
            final int oldVersion, final int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    /**
     * @param username username of the user
     * @param password password of the user
     * @return the row number that was effected
     */
    public final long insertIntoUserTable(final String username,
            final String password) {

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
    /**
     * @param username username of the user
     * @param password password of the user
     * @return Cursor object that has the return from the database
     */
    public final Cursor getUserFromUserTable(final String username,
            final String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor test = db.rawQuery("SELECT * FROM "
                +
                "users WHERE username = ? AND password = ?",
                new String[] {username, password});
        return test;
    }
    /**
     * @param accountName the name of the account
     * @param balance the balance of the account
     * @param username username of the user
     * @return the row in the db that was inserted to
     */
    public final long insertIntoAccountsTable(final String accountName,
            final String balance, final String username) {
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
    /**
     * @param username username of the user
     * @return Cursor object that contains the return of the database
     */
    public final Cursor getAccountFromAccountTable(final String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor test = db.rawQuery("SELECT * FROM accounts WHERE username = ?"
                , new String[] {username});
        return test;
    }
    /**
     * @param username username of the user
     * @param accountName name of the account
     * @return Cursor object that contains the return of the database
     */
    public final Cursor getBalanceFromAccountTable(final String username,
            final String accountName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor test = db.rawQuery("SELECT * FROM accounts where username = ? "
                +
                "AND accountname = ?"
                , new String[] {username, accountName});
        return test;
    }
    /**
     * @param username username of the user
     * @param accountName name of the account
     * @param newAccountBalance the new account balance
     * @return true if the update the successful
     */
    public final boolean updateBalance(final String username,
            final String accountName, final String newAccountBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE accounts SET balance = ? WHERE "
                +
                "username = ? AND accountname = ?"
                , new String[] {newAccountBalance, username, accountName});
        return true;
    }
    /**
     * @param transactionType the type of transaction
     * @param username username of the user
     * @param accountName name of the account
     * @param transactionAmount the amount of the transaction
     * @param transactionCategory the category of the transaction
     * @return the row number that the information was added to
     */
    public final long addTransactionToAccountHistory(
            final String transactionType,
            final String username, final String accountName,
            final String transactionAmount,
            final String transactionCategory) {
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
        values.put(FeedEntry.COLUMN_NAME_TRANSACTION_CATEGORY,
                transactionCategory);
        values.put(FeedEntry.COLUMN_NAME_DATE_TIME, getDateTime());
        long newRowId;
        newRowId = db.insert(
                 FeedEntry.TRANSACTION_TABLE_NAME,
                 null,
                 values);
        return newRowId;
    }
    /**
     * @param username username of the user
     * @param fromDate the date to start searching from
     * @param toDate the date to stop searching
     * @return Cursor object that contains the return from the database
     */
    public final Cursor getCategoryReportInformation(final String username,
            final String fromDate, final String toDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor test = db.rawQuery("SELECT * FROM transactions where "
                +
                "username = ? AND transactiontype = ? AND "
                +
                "timeoftransaction between datetime(?) AND datetime(?)",
                new String[] {username, "Withdrawal", fromDate, toDate});
        return test;
    }
    /**
     * @return the formated string of datatime
     */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
