package br.com.ufersa.qwater.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.WaterSourceAdapter;
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.beans.WaterSource;
import br.com.ufersa.qwater.database.AppDatabase;

public class StoreWaterReportActivity extends AppCompatActivity {

    private EditText date;
    private DatePickerDialog datePickerDialog;
    private AppDatabase appDatabase;
    private RecyclerView recyclerView;
    private ConstraintLayout dateLayout;
    private CoordinatorLayout listLayout;
    private WaterReport waterReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_date);

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
                datePickerDialog = new DatePickerDialog(StoreWaterReportActivity.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            // set day of month , month and year value in the edit text
                            date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            //
                            Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                            waterReport.setWatDate(calendar.getTimeInMillis());

                            dateLayout.setVisibility(View.GONE);
                            listLayout.setVisibility(View.VISIBLE);
                        }
                    }, mYear, mMonth, mDay);

                datePickerDialog.show();
                // Tentei usar o método setButton no dialog, mas deu algum conflito com o onDateSet

            }
        });




        StoreWaterReportActivity.AsyncRead asyncRead = new StoreWaterReportActivity.AsyncRead();
        asyncRead.execute();


    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    private void initiate() {

        dateLayout = findViewById(R.id.DATE_LAYOUT);
        listLayout = findViewById(R.id.WATER_SAMPLES_LAYOUT);

        appDatabase = AppDatabase.getInstance(StoreWaterReportActivity.this);

        date = findViewById(R.id.DATE_EDIT);

        recyclerView = findViewById(R.id.WATER_SOURCE_RECYCLER_VIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(StoreWaterReportActivity.this));

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("waterReport")){

            waterReport = getIntent().getParcelableExtra("waterReport");


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
            Toast.makeText(StoreWaterReportActivity.this, "Amostra inserida", Toast.LENGTH_SHORT).show();

        }
    }


    private class AsyncRead extends AsyncTask<Void, Void, List<WaterSource>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<WaterSource> doInBackground(Void... voids) {

            return appDatabase.waterSourceDao().getAll();
        }

        @Override
        protected void onPostExecute(List<WaterSource> waterSources) {
            RecyclerView.Adapter adapter = new WaterSourceAdapter(0, getApplicationContext(), waterSources);
            recyclerView.setAdapter(adapter);

        }
    }



}
