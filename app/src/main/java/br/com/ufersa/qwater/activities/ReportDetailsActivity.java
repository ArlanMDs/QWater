package br.com.ufersa.qwater.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Report;
import br.com.ufersa.qwater.database.AppDatabase;

import static br.com.ufersa.qwater.util.Flags.DELETE_REPORT;
import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.REPORT;
import static br.com.ufersa.qwater.util.Flags.SEE_REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE_REPORT;

public class ReportDetailsActivity extends AppCompatActivity{

    private TextView cea, ca, mg, k, na, co3, hco3, cl, pH, sourceName, correctedSAR, date, b, so4;
    private Report report;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        initiate();

        getIncomingIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch(item.getItemId()){
            case R.id.action_see:

                intent = new Intent(ReportDetailsActivity.this, AnalyzeReportActivity.class);
                intent.putExtra(GOING_TO, SEE_REPORT);
                intent.putExtra(REPORT, report);
                startActivity(intent);

                return true;
            case R.id.action_edit:

                intent = new Intent(ReportDetailsActivity.this, MainActivity.class);
                intent.putExtra(GOING_TO, UPDATE_REPORT);
                intent.putExtra(REPORT, report);
                startActivity(intent);

                return true;
            case R.id.action_delete:

                new AlertDialog.Builder(this)
                        .setTitle(R.string.deletando_relatorio)
                        .setMessage(R.string.certeza_deletar_relatorio)
                        .setPositiveButton(R.string.sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteReport();
                                    }
                                })
                        .setNegativeButton(R.string.nao, null)
                        .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteReport() {
        try {
            new AsyncDelete().execute();

            // se eu der somente um finish(), a lista de relatórios não atualiza...
            Intent intent = new Intent(ReportDetailsActivity.this, MainActivity.class);
            intent.putExtra(GOING_TO, DELETE_REPORT);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, R.string.erro_deletar_relatorio, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ReportDetailsActivity.this, MainActivity.class));
        }
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

        Toolbar mTopToolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(mTopToolbar);

        // prepara o bd
        appDatabase = AppDatabase.getInstance(ReportDetailsActivity.this);
    }

    private String formatDate(long timeInMillis){
        //TODO rever formato da data para internacionalizaço
        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(timeInMillis));

    }

    private class AsyncDelete extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.reportDao().delete(report);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(ReportDetailsActivity.this, R.string.relatorio_deletado, Toast.LENGTH_SHORT).show();
        }
    }
}
