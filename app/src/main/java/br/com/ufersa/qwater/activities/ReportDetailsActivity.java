package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Report;

public class ReportDetailsActivity extends AppCompatActivity {

    private TextView cea, ca, mg, k, na, co3, hco3, cl, pH, normalSAR, correctedSAR, date, b, so4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        initiate();

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("report")){

            Report report = getIntent().getParcelableExtra("report");

            cea.setText(String.valueOf(report.getCea()));
            ca.setText(String.valueOf(report.getCa()));
            mg.setText(String.valueOf(report.getMg()));
            k.setText(String.valueOf(report.getK()));
            na.setText(String.valueOf(report.getNa()));
            co3.setText(String.valueOf(report.getCo3()));
            hco3.setText(String.valueOf(report.getHco3()));
            cl.setText(String.valueOf(report.getCl()));
            pH.setText(String.valueOf(report.getPh()));
            normalSAR.setText(String.valueOf(report.getNormalSar()));
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
        normalSAR = findViewById(R.id.RAS_VALUE);
        correctedSAR = findViewById(R.id.CORRECTED_SAR_VALUE);
        date = findViewById(R.id.CREATED_AT_VALUE);
        b = findViewById(R.id.B_VALUE);
        so4 = findViewById(R.id.SO4_VALUE);

        Button delete = findViewById(R.id.DETAILS_DELETE_BUTTON);//TODO implementar delete

    }

    private String formatDate(long timeInMillis){
        String date;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeInMillis);

        date =  String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + "/" +
                String.valueOf(cal.get(Calendar.MONTH)) + "/" +
                String.valueOf(cal.get(Calendar.YEAR));

        return date;
    }

}
