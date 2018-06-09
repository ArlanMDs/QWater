package br.com.ufersa.qwater.activities;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.database.AppDatabase;

import static br.com.ufersa.qwater.models.MyApp.getContext;

public class StoreWaterReportActivity extends AppCompatActivity {

    EditText date;
    DatePickerDialog datePickerDialog;
    private AppDatabase appDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_water_report);

        initiate();

        getIncomingIntent();

        // initiate the date picker and a button
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(StoreWaterReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });






    }

    private void initiate() {

        appDatabase = AppDatabase.getInstance(getContext());

        date = findViewById(R.id.DATE_EDIT);


    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("waterReport")){

            WaterReport waterReport = getIntent().getParcelableExtra("waterReport");

           // date.setText(formatDate(waterReport.getWatDate()));
        }
    }

    private class AsyncInsert extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // waterReport.setWat_souName("aaaaa");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            //appDatabase.waterReportDao().insert(waterReport);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(), "Amostra inserida", Toast.LENGTH_SHORT).show();

        }
    }



}
