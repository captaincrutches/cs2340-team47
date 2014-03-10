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

public class Reports extends Activity {
	
	private String username;
	private String reportType;
	private static String fromDate;
	private static String toDate;
	private static TextView fromDateView;
	private static TextView toDateView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		
		fromDateView = (TextView) findViewById(R.id.report_from_date);
		toDateView = (TextView) findViewById(R.id.report_to_date);
		
		Spinner spinner = (Spinner) findViewById(R.id.reports_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.reports_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
		        // An item was selected. You can retrieve the selected item using
		        reportType = parent.getItemAtPosition(pos).toString();
		        
		    }

		    public void onNothingSelected(AdapterView<?> parent) {
		        // Another interface callback
		    }               
			}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reports, menu);
		return true;
	}
	
	public void generateReport(View view)
	{
		if (toDate != null && fromDate != null)
		{
			if (reportType.equals("Spending Category"))
			{
				Intent intent = new Intent(this, SpendingCategoryReport.class);
				intent.putExtra("username", username);
				intent.putExtra("fromDate", fromDate);
				intent.putExtra("toDate", toDate);
				toDate = null;
				fromDate = null;
				startActivity(intent);
			}
		}
		else
		{
			TextView errorMessage = (TextView) findViewById(R.id.generate_report_failed);
			errorMessage.setTextColor(Color.RED);
		}
	}
	
	public void showFromDatePickerDialog(View view) {
	    DialogFragment newFragment = new DatePickerFragmentFrom();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public void showToDatePickerDialog(View view) {
	    DialogFragment newFragment = new DatePickerFragmentTo();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	public static class DatePickerFragmentFrom extends DialogFragment implements DatePickerDialog.OnDateSetListener 
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
			String yearSelected = String.valueOf(year);
			String monthSelected = String.valueOf(month + 1);
			if (monthSelected.length() < 2)
			{
				monthSelected = "0" + monthSelected;
			}
			String daySelected = String.valueOf(day);
			if (daySelected.length() < 2)
			{
				daySelected = "0" + daySelected;
			}
			fromDate = yearSelected + "-" + monthSelected + "-" + daySelected + " 00:00:00";
			String fromDateToShow = monthSelected + "-" + daySelected + "-" + yearSelected;
			fromDateView.setText(fromDateToShow);
			
		}
	}
	
	public static class DatePickerFragmentTo extends DialogFragment implements DatePickerDialog.OnDateSetListener 
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
			String yearSelected = String.valueOf(year);
			String monthSelected = String.valueOf(month + 1);
			if (monthSelected.length() < 2)
			{
				monthSelected = "0" + monthSelected;
			}
			String daySelected = String.valueOf(day);
			if (daySelected.length() < 2)
			{
				daySelected = "0" + daySelected;
			}
			toDate = yearSelected + "-" + monthSelected + "-" + daySelected + " 23:59:59";
			
			String toDateToShow = monthSelected + "-" + daySelected + "-" + yearSelected;
			toDateView.setText(toDateToShow);
		}
	}

}
