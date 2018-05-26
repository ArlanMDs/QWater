package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Delete;
import br.com.ufersa.qwater.models.WaterSample;

public class WaterSamplesDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_sample_details);

        Intent i = getIntent();
        final WaterSample waterSample = i.getParcelableExtra("waterSample");

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

        cea.setText(String.valueOf(waterSample.getCea()));
        ca.setText(String.valueOf(waterSample.getCa()));
        mg.setText(String.valueOf(waterSample.getMg()));
        k.setText(String.valueOf(waterSample.getK()));
        na.setText(String.valueOf(waterSample.getNa()));
        co3.setText(String.valueOf(waterSample.getCo3()));
        hco3.setText(String.valueOf(waterSample.getHco3()));
        cl.setText(String.valueOf(waterSample.getCl()));
        rasNormal.setText(String.valueOf(waterSample.getNormalSAR()));
        rasCorrigido.setText(String.valueOf(waterSample.getCorrectedSAR()));
        createdAt.setText(formatDate(waterSample.getCreatedAt()));

        Button delete = findViewById(R.id.DETAILS_DELETE_BUTTON);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().removeReport(waterSample);
                Toast.makeText(WaterSamplesDetailsActivity.this, "Relatório deletado.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(WaterSamplesDetailsActivity.this, StoredWaterSamplesActivity.class);
                startActivity(intent);

            }
        });

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
        Intent intent = new Intent(WaterSamplesDetailsActivity.this, StoredWaterSamplesActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
