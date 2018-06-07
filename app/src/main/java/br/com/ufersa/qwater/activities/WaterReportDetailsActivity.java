package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class WaterReportDetailsActivity extends AppCompatActivity {

    private TextView cea, ca, mg, k, na, co3, hco3, cl, pH, normalSAR, correctedSAR, date;

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
        }
    }

    private void initiate(){
        cea = findViewById(R.id.CEA_DETAILS);
        ca = findViewById(R.id.CA_DETAILS);
        mg = findViewById(R.id.MG_DETAILS);
        k = findViewById(R.id.K_DETAILS);
        na = findViewById(R.id.NA_DETAILS);
        co3 = findViewById(R.id.CO3_DETAILS);
        hco3 = findViewById(R.id.HCO3_DETAILS);
        cl = findViewById(R.id.CL_DETAILS);
        pH = findViewById(R.id.PH_DETAILS);
        normalSAR = findViewById(R.id.RAS_DETAILS);
        correctedSAR = findViewById(R.id.CORRECTED_SAR_DETAILS);
        date = findViewById(R.id.CREATED_AT_DETAILS);
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}
