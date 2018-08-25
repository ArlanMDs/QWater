package br.com.ufersa.qwater.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.ufersa.qwater.R;

import static br.com.ufersa.qwater.util.Flags.CALLING_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.INSERT_SELECT_SOURCE;
import static br.com.ufersa.qwater.util.Flags.SAVE_REPORT_ACTIVITY;

public class SaveReportActivity extends AppCompatActivity {

    private TextView date, source;
    private DatePickerDialog datePickerDialog;
    private Button saveButton;
    private boolean dateOK, sourceOK;
    private Intent intent;
    private long reportDate;
    private int sourceID;
    private String sourceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_report);
        initiate();
        getIncomingIntent();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SaveReportActivity.this, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        //intent.putExtra("date", calendar.getTimeInMillis());
                        reportDate = calendar.getTimeInMillis();
                        dateOK = true;

                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
                // Tentei usar o método setButton no dialog, mas deu algum conflito com o onDateSet

            }
        });

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaveReportActivity.this, ListSourcesActivity.class);
                intent.putExtra(CALLING_ACTIVITY, SAVE_REPORT_ACTIVITY);
                startActivityForResult(intent, INSERT_SELECT_SOURCE);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateOK && sourceOK) {
                    intent.putExtra("date", reportDate);
                    intent.putExtra("sourceID", sourceID);
                    intent.putExtra("sourceName", sourceName);

                    setResult(RESULT_OK, intent);
                    finish();
                }else
                    Toast.makeText(SaveReportActivity.this, R.string.selecione_a_data_e_fonte, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initiate() {
        dateOK = false;
        sourceOK = false;
        intent = new Intent();
        date = findViewById(R.id.DATE_EDIT);
        source = findViewById(R.id.SOURCE_EDIT);
        saveButton = findViewById(R.id.SAVE_CONFIRM_BUTTON);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("sourceName")) {
            sourceName = getIntent().getStringExtra("sourceName");
            sourceID = getIntent().getIntExtra("sourceID", 0);
            reportDate = getIntent().getLongExtra("reportDate", System.currentTimeMillis());
            dateOK = true;
            sourceOK = true;

            source.setText(sourceName);
            date.setText(formatDate(reportDate));

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case INSERT_SELECT_SOURCE:
                if(resultCode == RESULT_OK) {

                    sourceID = data.getIntExtra("sourceID", 0);
                    sourceName = data.getStringExtra("sourceName");

                    source.setText(data.getStringExtra("sourceName"));
                    sourceOK = true;
                }
            break;

        }
    }

    private String formatDate(long timeInMillis){
        //TODO rever formato da data para internacionalizaço
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timeInMillis));

    }

}
