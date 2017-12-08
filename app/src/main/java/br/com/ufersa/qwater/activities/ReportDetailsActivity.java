package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Delete;
import br.com.ufersa.qwater.models.Report;

public class ReportDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        Intent i = getIntent();
        final Report report = i.getParcelableExtra("report");

        TextView cea = (TextView) findViewById(R.id.DETAILS_CEA);
        TextView ca = (TextView)findViewById(R.id.DETAILS_CA);
        TextView mg = (TextView)findViewById(R.id.DETAILS_MG);
        TextView k = (TextView)findViewById(R.id.DETAILS_K);
        TextView na = (TextView)findViewById(R.id.DETAILS_NA);
        TextView co3 = (TextView)findViewById(R.id.DETAILS_CO3);
        TextView hco3 = (TextView)findViewById(R.id.DETAILS_HCO3);
        TextView cl = (TextView)findViewById(R.id.DETAILS_CL);
        TextView rasNormal = (TextView)findViewById(R.id.DETAILS_RAS);
        TextView rasCorrigido = (TextView)findViewById(R.id.DETAILS_RAS_CORRIGIDO);
        TextView createdAt = (TextView)findViewById(R.id.DETAILS_CREATEDAT);

        cea.setText(String.valueOf(report.getCea()));
        ca.setText(String.valueOf(report.getCa()));
        mg.setText(String.valueOf(report.getMg()));
        k.setText(String.valueOf(report.getK()));
        na.setText(String.valueOf(report.getNa()));
        co3.setText(String.valueOf(report.getCo3()));
        hco3.setText(String.valueOf(report.getHco3()));
        cl.setText(String.valueOf(report.getCl()));
        rasNormal.setText(String.valueOf(report.getNormalSAR()));
        rasCorrigido.setText(String.valueOf(report.getCorrectedSAR()));
        createdAt.setText(formatDate(report.getCreatedAt()));

        Button delete = (Button)findViewById(R.id.DETAILS_DELETE_BUTTON);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Delete().removeReport(report);
                Toast.makeText(ReportDetailsActivity.this, "Relatório deletado.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ReportDetailsActivity.this, SavedReportsActivity.class);
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
        Intent intent = new Intent(ReportDetailsActivity.this, SavedReportsActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
