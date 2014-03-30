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
/**
 * @author asomani7
 */
public class SpendingCategoryReport extends Activity {
    /**The report data.*/
    private List<Map<String, String>> reportData =
            new ArrayList<Map<String, String>>();
    /**The username of the user.*/
    private String username;
    /**The to date to search until.*/
    private String toDate;
    /**The from date to start searching from.*/
    private String fromDate;
    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
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
        final ListView reportList = (ListView)
                findViewById(R.id.category_amount_list);
        reportList.setAdapter(adapter);
    }
    /**
     * Method to convert the Cursor object to a Map.
     * @param databaseReturn the Cursor object to convert
     */
    public final void convertCursorToMap(final Cursor databaseReturn) {
        int count = databaseReturn.getCount();
        Resources res = getResources();
        String[] categories = res.getStringArray(R.array.category_array);
        for (int i = 0; i < categories.length; i++) {
            databaseReturn.moveToFirst();
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("category", categories[i]);
            double totalAmount = 0;
            for (int j = 0; j < count; j++) {
                if (databaseReturn.getString(databaseReturn.getColumnIndex(
                        "transactioncategory")).equals(categories[i])) {
                    totalAmount += Double.parseDouble(databaseReturn.getString(
                            databaseReturn.getColumnIndex("transactionamount"))
                            );
                }
                databaseReturn.moveToNext();
            }
            datum.put("amount", String.valueOf(totalAmount));
            reportData.add(datum);
        }
    }
    /**
     * Method to transition to the my accounts page.
     * @param view android requires this
     */
    public final void transitionToMyAccountsPage(final View view) {
        Intent intent = new Intent(this, MyAccounts.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }
    /**
     * Method to get the data for the report from the database.
     * @param user username of the user
     * @param fromDatee date to start searching from
     * @param toDatee date to stop searching
     * @return Cursor object with the database return
     */
    public final Cursor getDataFromDatabase(final String user,
            final String fromDatee, final String toDatee) {
        DatabaseInterfacer database = new DatabaseInterfacer(
                getApplicationContext());
        Cursor databaseReturn = database.getCategoryReportInformation(user,
                fromDatee, toDatee);
        return databaseReturn;
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.spending_category_report, menu);
        return true;
    }

}
