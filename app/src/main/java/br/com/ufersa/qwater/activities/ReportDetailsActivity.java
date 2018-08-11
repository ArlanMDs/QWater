package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Report;

import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE;

public class ReportDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView cea, ca, mg, k, na, co3, hco3, cl, pH, sourceName, correctedSAR, date, b, so4;
    private Report report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        initiate();

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("report")){

            report = getIntent().getParcelableExtra("report");

            cea.setText(String.valueOf(report.getCea()));
            ca.setText(String.valueOf(report.getCa()));
            mg.setText(String.valueOf(report.getMg()));
            k.setText(String.valueOf(report.getK()));
            na.setText(String.valueOf(report.getNa()));
            co3.setText(String.valueOf(report.getCo3()));
            hco3.setText(String.valueOf(report.getHco3()));
            cl.setText(String.valueOf(report.getCl()));
            pH.setText(String.valueOf(report.getPh()));
            sourceName.setText(String.valueOf(report.getSouName()));
            correctedSAR.setText(String.valueOf(report.getCorrectedSar()));
            date.setText(formatDate(report.getDate()));
            b.setText(String.valueOf(report.getB()));
            so4.setText(String.valueOf(report.getSo4()));
        }
    }

    private void initiate(){
        cea = findViewById(R.id.CEA_VALUE);
        ca = findViewById(R.id.CA_VALUE);
        mg = findViewById(R.id.MG_VALUE);
        k = findViewById(R.id.K_VALUE);
        na = findViewById(R.id.NA_VALUE);
        co3 = findViewById(R.id.CO3_VALUE);
        hco3 = findViewById(R.id.HCO3_VALUE);
        cl = findViewById(R.id.CL_VALUE);
        pH = findViewById(R.id.PH_VALUE);
        sourceName = findViewById(R.id.SOURCE_NAME);
        correctedSAR = findViewById(R.id.CORRECTED_SAR_VALUE);
        date = findViewById(R.id.CREATED_AT_VALUE);
        b = findViewById(R.id.B_VALUE);
        so4 = findViewById(R.id.SO4_VALUE);

        ImageButton delete = findViewById(R.id.DETAILS_DELETE);
        delete.setOnClickListener(this);

        ImageButton edit = findViewById(R.id.DETAILS_EDIT);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.DETAILS_DELETE:


                break;

            case R.id.DETAILS_EDIT:

                Intent intent = new Intent(ReportDetailsActivity.this, MainActivity.class);
                intent.putExtra(GOING_TO, UPDATE);
                intent.putExtra(REPORT, report);
                startActivity(intent);

                break;
        }
    }

    private String formatDate(long timeInMillis){
//        String date;
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(timeInMillis);
//
//        date =  String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
//                String.valueOf(cal.get(Calendar.MONTH)) + "/" +
////                String.valueOf(cal.get(Calendar.YEAR));
//
//        return date;

        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timeInMillis));

    }


}
