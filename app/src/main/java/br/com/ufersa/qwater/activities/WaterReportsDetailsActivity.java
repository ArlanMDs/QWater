package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class WaterReportsDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_details);

        Intent i = getIntent();
        final WaterReport waterReport = i.getParcelableExtra("waterReport");

        TextView cea = findViewById(R.id.DETAILS_CEA);
        TextView ca = findViewById(R.id.DETAILS_CA);
        TextView mg = findViewById(R.id.DETAILS_MG);
        TextView k = findViewById(R.id.DETAILS_K);
        TextView na = findViewById(R.id.DETAILS_NA);
        TextView co3 = findViewById(R.id.DETAILS_CO3);
        TextView hco3 = findViewById(R.id.DETAILS_HCO3);
        TextView cl = findViewById(R.id.DETAILS_CL);
        TextView rasNormal = findViewById(R.id.DETAILS_RAS);
        TextView rasCorrigido = findViewById(R.id.DETAILS_RAS_CORRIGIDO);
        TextView createdAt = findViewById(R.id.DETAILS_CREATEDAT);

        cea.setText(String.valueOf(waterReport.getWatCea()));
        ca.setText(String.valueOf(waterReport.getWatCa()));
        mg.setText(String.valueOf(waterReport.getWatMg()));
        k.setText(String.valueOf(waterReport.getWatK()));
        na.setText(String.valueOf(waterReport.getWatNa()));
        co3.setText(String.valueOf(waterReport.getWatCo3()));
        hco3.setText(String.valueOf(waterReport.getWatHco3()));
        cl.setText(String.valueOf(waterReport.getWatCl()));
        rasNormal.setText(String.valueOf(waterReport.getWatNormalSar()));
        rasCorrigido.setText(String.valueOf(waterReport.getWatCorrectedSar()));
        createdAt.setText(formatDate(waterReport.getWatCreatedAt()));

        Button delete = findViewById(R.id.DETAILS_DELETE_BUTTON);



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
