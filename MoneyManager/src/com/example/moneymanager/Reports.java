package com.example.moneymanager;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
/**
 * @author asomani7
 */
public class Reports extends Activity {
    /**username of the user.*/
    private String username;
    /**type of report.*/
    private String reportType;
    /**from date to start searching from.*/
    private static String fromDate;
    /**to date to search until.*/
    private static String toDate;
    /**from date calendar that shows on the screen.*/
    private static TextView fromDateView;
    /**to date calendar that shows on the screen.*/
    private static TextView toDateView;

    @Override
    protected final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        fromDateView = (TextView) findViewById(R.id.report_from_date);
        toDateView = (TextView) findViewById(R.id.report_to_date);
        Spinner spinner = (Spinner) findViewById(R.id.reports_spinner);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.reports_array,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent,
                    final View view,
                    final int pos, final long id) {
                reportType = parent.getItemAtPosition(pos).toString();
            }
            @Override
            public final void onNothingSelected(final AdapterView<?> parent) {
                // Another interface callback
            }
            }
        );
    }

    @Override
    public final boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reports, menu);
        return true;
    }
    /**
     * Method to generate the report.
     * @param view android requires this
     */
    public final void generateReport(final View view) {
        if (toDate != null && fromDate != null) {
            if (reportType.equals("Spending Category")) {
                Intent intent = new Intent(this, SpendingCategoryReport.class);
                intent.putExtra("username", username);
                intent.putExtra("fromDate", fromDate);
                intent.putExtra("toDate", toDate);
                toDate = null;
                fromDate = null;
                startActivity(intent);
            }
        } else {
            TextView errorMessage =
                    (TextView) findViewById(R.id.generate_report_failed);
            errorMessage.setTextColor(Color.RED);
        }
    }
    /**
     * Method to show the from date picker.
     * @param view android requires this
     */
    public final void showFromDatePickerDialog(final View view) {
        DialogFragment newFragment = new DatePickerFragmentFrom();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    /**
     * Method to show the to date picker.
     * @param view android requires this
     */
    public final void showToDatePickerDialog(final View view) {
        DialogFragment newFragment = new DatePickerFragmentTo();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    /**
     * @author asomani7
     */
    public static class DatePickerFragmentFrom extends
    DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        /**
         * @param view android requires this
         * @param year the year
         * @param month the month
         * @param day the day
         */
        public final void onDateSet(final DatePicker view,
                final int year, final int month, final int day) {
        // Do something with the date chosen by the user
            String yearSelected = String.valueOf(year);
            String monthSelected = String.valueOf(month + 1);
            if (monthSelected.length() < 2) {
                monthSelected = "0" + monthSelected;
            }
            String daySelected = String.valueOf(day);
            if (daySelected.length() < 2) {
                daySelected = "0" + daySelected;
            }
            fromDate = yearSelected + "-" + monthSelected
                    +
                    "-" + daySelected + " 00:00:00";
            String fromDateToShow = monthSelected + "-" + daySelected
                    +
                    "-" + yearSelected;
            fromDateView.setText(fromDateToShow);
        }
    }
    /**
     * @author asomani7
     */
    public static class DatePickerFragmentTo extends
            DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public final Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        /**
         * @param view android requires this
         * @param year the year
         * @param month the month
         * @param day the day
         */
        public final void onDateSet(final DatePicker view, final int year,
                final int month, final int day) {
        // Do something with the date chosen by the user
            String yearSelected = String.valueOf(year);
            String monthSelected = String.valueOf(month + 1);
            if (monthSelected.length() < 2) {
                monthSelected = "0" + monthSelected;
            }
            String daySelected = String.valueOf(day);
            if (daySelected.length() < 2) {
                daySelected = "0" + daySelected;
            }
            toDate = yearSelected + "-" + monthSelected + "-"
                    + daySelected + " 23:59:59";
            String toDateToShow = monthSelected + "-"
                    +
                    daySelected + "-" + yearSelected;
            toDateView.setText(toDateToShow);
        }
    }

}
