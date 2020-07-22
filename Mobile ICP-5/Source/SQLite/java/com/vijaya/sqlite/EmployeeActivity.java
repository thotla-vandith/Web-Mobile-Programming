package com.vijaya.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.vijaya.sqlite.databinding.ActivityEmployeeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmployeeActivity extends AppCompatActivity {

    private ActivityEmployeeBinding bind;
    private static final String ACTIVITY_TAG = "EmployeeActivity";
    private String CurrentFirstName, CurrentLastName;
    private int CurrentEmployeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_employee);

        bind.recycleView.setLayoutManager(new LinearLayoutManager(this));

        String[] queryCols = new String[]{"_id", SampleDBContract.Employer.COLUMN_NAME};
        String[] adapterCols = new String[]{SampleDBContract.Employer.COLUMN_NAME};
        int[] adapterRowViews = new int[]{android.R.id.text1};

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        Cursor cursor = database.query(
                SampleDBContract.Employer.TABLE_NAME,     // The table to query
                queryCols,                                // The columns to return
                null,                             // The columns for the WHERE clause
                null,                          // The values for the WHERE clause
                null,                             // don't group the rows
                null,                              // don't filter by row groups
                null                              // don't sort
        );

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this, android.R.layout.simple_spinner_item, cursor, adapterCols, adapterRowViews, 0);
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bind.employerSpinner.setAdapter(cursorAdapter);

        bind.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToDB();
            }
        });
        bind.updateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                updateDB();
            }
        });
        bind.deleteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteFromDB();
            }
        });
        bind.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFromDB();
            }
        });
    }

    private void updateDB() {

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(SampleDBContract.Employee.COLUMN_FIRSTNAME, bind.firstnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_LASTNAME, bind.lastnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION, bind.jobDescEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_EMPLOYED_DATE, bind.employedEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_EMPLOYER_ID,
                ((Cursor) bind.employerSpinner.getSelectedItem()).getInt(0));
        String whereClause = SampleDBContract.Employee.COLUMN_FIRSTNAME + " like ? AND " + SampleDBContract.Employee.COLUMN_LASTNAME+ " like ?";
        String[] whereArgs = {"%" + CurrentFirstName + "%", "%" + CurrentLastName + "%"};

        database.update(SampleDBContract.Employee.TABLE_NAME,values,whereClause,whereArgs);
        Toast.makeText(this, "Updated " + CurrentFirstName + " " + CurrentLastName, Toast.LENGTH_LONG).show();
        CurrentFirstName = "";
        CurrentLastName = "";
        readFromDB();
    }

    private void deleteFromDB() {

        Toast.makeText(this, "Deleting Information." , Toast.LENGTH_LONG).show();
        readFromDB();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();
        String firstname = bind.firstnameEditText.getText().toString();
        String lastname = bind.lastnameEditText.getText().toString();
        String whereClause = SampleDBContract.Employee.COLUMN_FIRSTNAME + " like ? AND " + SampleDBContract.Employee.COLUMN_LASTNAME + " like ?";
        String[] whereArgs = {"%" + firstname + "%", "%" + lastname + "%"};
        database.delete(SampleDBContract.Employee.TABLE_NAME, whereClause, whereArgs);
        bind.firstnameEditText.setText("");
        bind.lastnameEditText.setText("");
        readFromDB();
    }

    private void saveToDB() {
        CurrentFirstName = bind.firstnameEditText.getText().toString();
        CurrentLastName = bind.lastnameEditText.getText().toString();
        CurrentEmployeeID = ((Cursor) bind.employerSpinner.getSelectedItem()).getInt(0);
        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SampleDBContract.Employee.COLUMN_FIRSTNAME, bind.firstnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_LASTNAME, bind.lastnameEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_JOB_DESCRIPTION, bind.jobDescEditText.getText().toString());
        values.put(SampleDBContract.Employee.COLUMN_EMPLOYER_ID,
                ((Cursor) bind.employerSpinner.getSelectedItem()).getInt(0));

        Log.d("getINT", ((Cursor) bind.employerSpinner.getSelectedItem()).getInt(0) + "");
        Log.d("getColumnName", ((Cursor) bind.employerSpinner.getSelectedItem()).getColumnName(0));

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    bind.dobEditText.getText().toString()));
            long date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_DATE_OF_BIRTH, date);

            calendar.setTime((new SimpleDateFormat("dd/MM/yyyy")).parse(
                    bind.employedEditText.getText().toString()));
            date = calendar.getTimeInMillis();
            values.put(SampleDBContract.Employee.COLUMN_EMPLOYED_DATE, date);
        } catch (Exception e) {
            Log.e(ACTIVITY_TAG, "Error", e);
            Toast.makeText(this, "Date is in the wrong format", Toast.LENGTH_LONG).show();
            return;
        }
        long newRowId = database.insert(SampleDBContract.Employee.TABLE_NAME, null, values);

        Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
    }

    private void readFromDB() {
        String firstname = bind.firstnameEditText.getText().toString();
        String lastname = bind.lastnameEditText.getText().toString();

        SQLiteDatabase database = new SampleDBSQLiteHelper(this).getReadableDatabase();

        String[] selectionArgs = {"%" + firstname + "%", "%" + lastname + "%"};

        Cursor cursor = database.rawQuery(SampleDBContract.SELECT_EMPLOYEE_WITH_EMPLOYER, selectionArgs);
        bind.recycleView.setAdapter(new SampleJoinRecyclerViewCursorAdapter(this, cursor));
        CurrentFirstName = firstname;
        CurrentLastName = lastname;
    }
}