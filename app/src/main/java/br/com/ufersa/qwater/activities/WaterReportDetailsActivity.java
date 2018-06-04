package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class WaterReportDetailsActivity extends AppCompatActivity {

    TextView cea ;
    TextView ca ;
    TextView mg;
    TextView k ;
    TextView na ;
    TextView co3 ;
    TextView hco3 ;
    TextView cl ;
    TextView rasNormal;
    TextView rasCorrigido ;
    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_report_details);

        findViewsIDs();

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
                rasNormal.setText(String.valueOf(waterReport.getWatNormalSar()));
                rasCorrigido.setText(String.valueOf(waterReport.getWatCorrectedSar()));
                date.setText(formatDate(waterReport.getWatDate()));
        }
    }

    private void findViewsIDs(){
        cea = findViewById(R.id.DETAILS_CEA);
        ca = findViewById(R.id.DETAILS_CA);
        mg = findViewById(R.id.DETAILS_MG);
        k = findViewById(R.id.DETAILS_K);
        na = findViewById(R.id.DETAILS_NA);
        co3 = findViewById(R.id.DETAILS_CO3);
        hco3 = findViewById(R.id.DETAILS_HCO3);
        cl = findViewById(R.id.DETAILS_CL);
        rasNormal = findViewById(R.id.DETAILS_RAS);
        rasCorrigido = findViewById(R.id.DETAILS_RAS_CORRIGIDO);
        date = findViewById(R.id.DETAILS_CREATEDAT);
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
