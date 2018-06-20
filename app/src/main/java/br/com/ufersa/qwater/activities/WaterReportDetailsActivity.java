package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class WaterReportDetailsActivity extends AppCompatActivity {

    private TextView cea, ca, mg, k, na, co3, hco3, cl, pH, normalSAR, correctedSAR, date, b, so4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_details);

        initiate();

        getIncomingIntent();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("waterReport")){

            WaterReport waterReport = getIntent().getParcelableExtra("waterReport");

            cea.setText(String.valueOf(waterReport.getWatCea()));
            ca.setText(String.valueOf(waterReport.getWatCa()));
            mg.setText(String.valueOf(waterReport.getWatMg()));
            k.setText(String.valueOf(waterReport.getWatK()));
            na.setText(String.valueOf(waterReport.getWatNa()));
            co3.setText(String.valueOf(waterReport.getWatCo3()));
            hco3.setText(String.valueOf(waterReport.getWatHco3()));
            cl.setText(String.valueOf(waterReport.getWatCl()));
            pH.setText(String.valueOf(waterReport.getWatPH()));
            normalSAR.setText(String.valueOf(waterReport.getWatNormalSar()));
            correctedSAR.setText(String.valueOf(waterReport.getWatCorrectedSar()));
            date.setText(formatDate(waterReport.getWatDate()));
            b.setText(String.valueOf(waterReport.getWatB()));
            so4.setText(String.valueOf(waterReport.getWatSo4()));
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
