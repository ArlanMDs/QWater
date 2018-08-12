package br.com.ufersa.qwater.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.ufersa.qwater.R;

public class SelectDateActivity extends AppCompatActivity {

    private EditText date;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        initiate();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SelectDateActivity.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        //
                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        //passData(calendar.getTimeInMillis());
                        //getActivity().getSupportFragmentManager().beginTransaction().remove(DateFragment.this).commit();

                        Intent intent = new Intent();
                        intent.putExtra("date", calendar.getTimeInMillis());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
                // Tentei usar o método setButton no dialog, mas deu algum conflito com o onDateSet

            }
        });
    }

    private void initiate(){

        date = findViewById(R.id.DATE_EDIT);
    }
}
