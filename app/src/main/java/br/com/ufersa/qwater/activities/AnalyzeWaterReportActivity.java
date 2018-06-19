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

public class AnalyzeWaterReportActivity extends AppCompatActivity implements View.OnClickListener{

    private Button SARButton;
    private Button salinityButton;
    private TextView correctedSARResultTextview, normalSARResultTextview, salinityTextview, SARClassificationTextview, ceaTextview;
    private int currentSARClassification, currentSalinityClassification;
    private WaterReport waterReport;
    private AppDatabase appDatabase;
    private final static int REQUEST_CODE_SELECT_DATE = 1, REQUEST_CODE_SELECT_SOURCE = 2;
    private boolean ableToClassifySAR = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_water_report);

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
    private void updateData(WaterReport waterReport){

        waterReport.setWatNormalSar(calculateNormalSAR(waterReport.getWatCa(), waterReport.getWatMg(), waterReport.getWatNa()));
        waterReport.setWatCorrectedSar(calculateCorrectedSAR(waterReport.getWatCa(),waterReport.getWatMg(),waterReport.getWatNa(),waterReport.getWatHco3(),waterReport.getWatCea()));

        // A classificação e armazenamento está sendo feito após o arredondamento das casas decimais!
        this.waterReport = waterReport;

        // Primeiro é categorizado a RAS, nesse método, a booleana ableToClassifySAR por ser colocada em false, então, algumas alterações são feitas no método UpdateUI()
        categorizeSAR();
        categorizeSalinity(waterReport.getWatCea());
        updateUI();

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

        if(ca != 0 && mg != 0 && na != 0) { // Calcula a RAS e arredonda o resultado
            SARCalculator sarCalculator = new SARCalculator();
            return formatFractionDigits(sarCalculator.calculateNormalSAR(1, ca, mg, na));//TODO SPINNNNER
        }else
            return Double.NaN;
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

        if(ca != 0 && mg != 0 && na != 0 && hco3 != 0 && cea != 0) {
            SARCalculator sarCalculator = new SARCalculator();
            return formatFractionDigits(sarCalculator.calculateCorrectedSAR(1, 1, ca, mg, na, cea, hco3));//TODO implementar as conversões dos spinners
        }else
            return Double.NaN;

    }

    private void updateUI(){

        //RAS
        if(ableToClassifySAR) {

            // Ras normal
            normalSARResultTextview.setText(String.valueOf(waterReport.getWatNormalSar()));
            SARButton.setVisibility(View.VISIBLE);

            // Ras corrigida
            correctedSARResultTextview.setText(String.valueOf(waterReport.getWatCorrectedSar()));
            SARClassificationTextview.setText("S" + String.valueOf(currentSARClassification));

        } else {
            normalSARResultTextview.setText(R.string.erro_calculo);
            correctedSARResultTextview.setText(R.string.erro_calculo);
        }

        // CEa
        if (waterReport.getWatCea() != 0) {
            salinityButton.setVisibility(View.VISIBLE);
            ceaTextview.setText(String.valueOf(waterReport.getWatCea()));
            salinityTextview.setText("C" + String.valueOf(currentSalinityClassification));
        }else{
            ceaTextview.setText("CEa não Informada");
            salinityTextview.setVisibility(View.GONE);
        }
    }

    /**
     * Arredenda o valor de um double para 4 casas decimais
     * @param value valor a ser arredondado
     * @return valor arredondado
     */
    private double formatFractionDigits(double value){
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

    private void categorizeSAR() {

        double correctedSAR = waterReport.getWatCorrectedSar();

        if(!Double.isNaN(correctedSAR)) {
            if (correctedSAR < 10.0)
                currentSARClassification = 1;
            else if (correctedSAR >= 10.0 && correctedSAR < 18.0)
                currentSARClassification = 2;
            else if (correctedSAR >= 18.0 && correctedSAR < 26.0)
                currentSARClassification = 3;
            else
                currentSARClassification = 4;
        }
        // Não é possível calcular a RAS, Mudanças serão feitas na UI
        else
            ableToClassifySAR = false;
    }

    /**
     * mostra uma janela contendo informações em texto sobre a classificação do RAS ou salinidade
     * @param classification RAS ou salinidade a ser classificada
     */
    private void showInfoDialogue(String classification){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        int resourceId = this.getResources().getIdentifier(classification, "string", this.getPackageName());
        builder.setTitle(getString(R.string.classificacao)+ " " + classification)
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
        appDatabase = AppDatabase.getInstance(AnalyzeWaterReportActivity.this);

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
        appDatabase = AppDatabase.getInstance(AnalyzeWaterReportActivity.this);
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
                // abre uma nova activity e passa o relatório, lá, será inserida a data da amostra e o nome da fonte
                Intent intent = new Intent(AnalyzeWaterReportActivity.this, SelectDateActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_DATE);

                break;
            case R.id.SHOW_HIDE_GRAPH1:

                Intent intent2 = new Intent(AnalyzeWaterReportActivity.this, PopupGraphActivity.class);
                startActivity(intent2);
                break;

        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_SELECT_DATE:

                if(resultCode == RESULT_OK) {
                    waterReport.setWatDate(data.getLongExtra("date", 0));

                    Intent intent = new Intent(AnalyzeWaterReportActivity.this, ListWaterSourcesActivity.class);
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

            Toast.makeText(AnalyzeWaterReportActivity.this, "Relatório armazenado com sucesso.", Toast.LENGTH_SHORT).show();
        }
    }


}
