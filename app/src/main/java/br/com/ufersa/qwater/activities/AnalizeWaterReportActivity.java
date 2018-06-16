package br.com.ufersa.qwater.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.NumberFormat;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.database.AppDatabase;
import br.com.ufersa.qwater.util.SARCalculator;

import static br.com.ufersa.qwater.util.MyApp.getContext;

public class AnalizeWaterReportActivity extends AppCompatActivity implements View.OnClickListener{

    private Button SARButton;
    private Button salinityButton;
    private TextView correctedSARResultTextview, normalSARResultTextview, salinityTextview, SARClassificationTextview, ceaTextview;
    private int currentSARClassification, currentSalinityClassification;
    private WaterReport waterReport;
    private AppDatabase appDatabase;
    private final static int REQUEST_CODE_SELECT_DATE = 1, REQUEST_CODE_SELECT_SOURCE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analize_water_report);

//        if (savedInstanceState != null) {
//            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
//
//        }
        initiate();
        getIncomingIntent();
    }

    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param waterReport relatório recebido da tab1
     */
    public void updateData(WaterReport waterReport){

        waterReport.setWatNormalSar(calculateNormalSAR(waterReport.getWatCa(), waterReport.getWatMg(), waterReport.getWatNa()));
        waterReport.setWatCorrectedSar(calculateCorrectedSAR(waterReport.getWatCa(),waterReport.getWatMg(),waterReport.getWatNa(),waterReport.getWatHco3(),waterReport.getWatCea()));

        // A classificação e armazenamento está sendo feito após o arredondamento das casas decimais!
        this.waterReport = waterReport;
        categorizeSAR(waterReport.getWatCorrectedSar());
        categorizeSalinity(waterReport.getWatCea());
        updateTextViews();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("waterReport")) {

            updateData((WaterReport) getIntent().getParcelableExtra("waterReport"));
        }
    }

    /**
     * Calcula a RAS normal
     * @param ca cálcio
     * @param mg magnésio
     * @param na sódio
     * @return valor da RAS normal ou zero em caso de erro
     */
    private double calculateNormalSAR(double ca, double mg, double na){
        SARCalculator sarCalculator;

        try {

            sarCalculator = new SARCalculator(ca, mg, na);
        }catch(Exception e){
            Toast.makeText(getContext(), getString(R.string.valorIncorreto), Toast.LENGTH_LONG).show();
            sarCalculator = null;
        }
        if(sarCalculator != null)
            return formatFractionDigits(sarCalculator.calculateNormalSAR(1));//TODO implementar as conversões do spinner

        return 0.0;
    }

    /**
     * Calcula a RAS corrigida
     * @param ca cálcio
     * @param mg magnésio
     * @param na sódio
     * @param hco3 bicarbonato
     * @param cea condutividade elétrica
     * @return valor da RAS corrigida, zero em caso de erro.
     */
    private double calculateCorrectedSAR(double ca, double mg, double na, double hco3, double cea) {
        SARCalculator sarCalculator;
        try {
            sarCalculator = new SARCalculator(ca, mg, na, cea, hco3);
        }catch(Exception e){
            Toast.makeText(getContext(), getString(R.string.valorIncorreto), Toast.LENGTH_LONG).show();
            sarCalculator = null;
        }
        if(sarCalculator != null)
            return formatFractionDigits(sarCalculator.calculateCorrectedSAR(1, 1));//TODO implementar as conversões dos spinners

        return 0.0;
    }


    private void updateTextViews(){
        //insere os valores nos campos de texto
        if(waterReport.getWatNormalSar() != 0)
            normalSARResultTextview.setText(String.valueOf(waterReport.getWatNormalSar()));
        else
            normalSARResultTextview.setText("Não foi possível calcular a RAS normal");

        if(waterReport.getWatCorrectedSar() != 0) {
            SARButton.setVisibility(View.VISIBLE);

            correctedSARResultTextview.setText(String.valueOf(waterReport.getWatCorrectedSar()));
            SARClassificationTextview.setText("S" + String.valueOf(currentSARClassification));

        }else{
            correctedSARResultTextview.setText("Não foi possível calcular a RAS corrigida");
        }

        if(waterReport.getWatCea() != 0) {
            salinityButton.setVisibility(View.VISIBLE);

            ceaTextview.setText(String.valueOf(waterReport.getWatCea()));
            salinityTextview.setText("C" + String.valueOf(currentSalinityClassification));
        }
    }

    private double formatFractionDigits(double value){
        //arredenda os valores para 4 casas decimais
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(format.format(value));

    }

    private void categorizeSalinity(double CEa) {

        if(CEa < 0.75)
            currentSalinityClassification = 1;
        else if(CEa >= 0.75 && CEa < 1.50)
            currentSalinityClassification = 2;
        else if(CEa >=1.50 && CEa < 3.0)
            currentSalinityClassification = 3;
        else
            currentSalinityClassification = 4;

    }

    private void categorizeSAR(double correctedSAR) {

        if(correctedSAR < 10.0)
            currentSARClassification = 1;
        else if(correctedSAR >= 10.0 && correctedSAR < 18.0)
            currentSARClassification = 2;
        else if(correctedSAR >=18.0 && correctedSAR < 26.0)
            currentSARClassification = 3;
        else
            currentSARClassification = 4;
    }

    /**
     * mostra uma janela contendo informações em texto sobre a classificação do RAS ou salinidade
     * @param classificacao RAS ou salinidade a ser classificada
     */
    public void showInfoDialogue(String classificacao){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        int resourceId = this.getResources().getIdentifier(classificacao, "string", this.getPackageName());
        builder.setTitle(getString(R.string.classificacao)+ " " + classificacao)
                .setMessage(getString(resourceId))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //fecha o alerta
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void initiate() {
        appDatabase = AppDatabase.getInstance(getContext());

        correctedSARResultTextview = findViewById(R.id.CORRECTED_SAR_RESULT_TEXTVIEW);
        normalSARResultTextview = findViewById(R.id.NORMAL_SAR_RESULT_TEXTVIEW);
        SARClassificationTextview = findViewById(R.id.SAR_CLASSIFICATION_TEXTVIEW);
        ceaTextview = findViewById(R.id.CEA_TEXTVIEW);
        salinityTextview = findViewById(R.id.SALINITY_TEXTVIEW);

        SARButton = findViewById(R.id.SAR_DETAILS_BUTTON);
        SARButton.setOnClickListener(this);

        salinityButton = findViewById(R.id.SALINITY_BUTTON);
        salinityButton.setOnClickListener(this);

        Button saveReportButton = findViewById(R.id.SAVE_WATER_REPORT_BUTTON);
        saveReportButton.setOnClickListener(this);

        Button showHideGraph1 = findViewById(R.id.SHOW_HIDE_GRAPH1);
        showHideGraph1.setOnClickListener(this);

        //prepara o bd
        appDatabase = AppDatabase.getInstance(AnalizeWaterReportActivity.this);
    }

    /**
     * manipula os clicks nos botões
     * @param v view que contém o ID do botão
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*
            a concatenação é para que seja feito apelas um método para chamar a caixa de diálogo,
            pois ela usa uma string com "S" ou "C" juntamente com o número da classificação para
            buscar o valor em strings.xml e montar a tela de dialog.
             */

            case R.id.SAR_DETAILS_BUTTON:
                showInfoDialogue("S" + currentSARClassification);

                break;
            case R.id.SALINITY_BUTTON:
                showInfoDialogue("C" + currentSalinityClassification);

                break;
            case R.id.SAVE_WATER_REPORT_BUTTON:
                //TODO ainda tenho que checar se a amostra pode ser salva. A ras pode estar com valor null, o que causa erro
                // abre uma nova activity e passa o relatório, lá, será inserida a data da amostra e o nome da fonte
                Intent intent = new Intent(AnalizeWaterReportActivity.this, SelectDateActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_DATE);

                break;
            case R.id.SHOW_HIDE_GRAPH1:

                Intent intent2 = new Intent(AnalizeWaterReportActivity.this, PopupGraphActivity.class);
                startActivity(intent2);
                break;

        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        //savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_SELECT_DATE:

                if(resultCode == RESULT_OK) {
                    waterReport.setWatDate(data.getLongExtra("date", 0));

                    Intent intent = new Intent(AnalizeWaterReportActivity.this, ListWaterSourcesActivity.class);
                    intent.putExtra("callingActivity", 1);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_SOURCE);

                }
            break;

            case REQUEST_CODE_SELECT_SOURCE:

                if(resultCode == RESULT_OK) {
                    waterReport.setWat_souID(data.getIntExtra("sourceID", 0));
                    waterReport.setWat_souName(data.getStringExtra("sourceName"));

                    // Pronto para armazenar no BD
                    new AsyncInsert().execute();
                }
            break;
        }
    }

    //TODO resolver o problema do leak https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private class AsyncInsert extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.waterReportDao().insert(waterReport);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(AnalizeWaterReportActivity.this, "Relatório armazenado com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }


}
