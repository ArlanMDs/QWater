package br.com.ufersa.qwater.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.NumberFormat;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.CreateWaterReportActivity;
import br.com.ufersa.qwater.activities.PopupGraphActivity;
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.database.AppDatabase;
import br.com.ufersa.qwater.models.SARCalculator;

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button SARButton, salinityButton, saveReportButton;
    private TextView correctedSARResultTextview, normalSARResultTextview, salinityTextview, SARClassificationTextview, ceaTextview;
    private int currentSARClassification, currentSalinityClassification;
    private WaterReport waterReport;
    private AppDatabase appDatabase;
    private final static int REQUEST_CODE_CREATE_REPORT = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appDatabase = AppDatabase.getInstance(getContext());
        initiate();

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
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        int resourceId = getContext().getResources().getIdentifier(classificacao, "string", getContext().getPackageName());
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
        View view = getView();

        correctedSARResultTextview = view.findViewById(R.id.CORRECTED_SAR_RESULT_TEXTVIEW);
        normalSARResultTextview = view.findViewById(R.id.NORMAL_SAR_RESULT_TEXTVIEW);
        SARClassificationTextview = view.findViewById(R.id.SAR_CLASSIFICATION_TEXTVIEW);
        ceaTextview = view.findViewById(R.id.CEA_TEXTVIEW);
        salinityTextview = view.findViewById(R.id.SALINITY_TEXTVIEW);

        SARButton = view.findViewById(R.id.SAR_DETAILS_BUTTON);
        SARButton.setOnClickListener(this);

        salinityButton = view.findViewById(R.id.SALINITY_BUTTON);
        salinityButton.setOnClickListener(this);

        saveReportButton = view.findViewById(R.id.SAVE_WATER_REPORT_BUTTON);
        saveReportButton.setOnClickListener(this);

        Button showHideGraph1 = view.findViewById(R.id.SHOW_HIDE_GRAPH1);
        showHideGraph1.setOnClickListener(this);

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

                //abre uma nova activity e passa o relatório, lá, será inserida a data da amostra e o nome da fonte
                Intent intent = new Intent(getContext(), CreateWaterReportActivity.class);
                intent.putExtra("waterReport", waterReport);
                startActivityForResult(intent, REQUEST_CODE_CREATE_REPORT);

                //new AsyncInsert().execute();

                break;
            case R.id.SHOW_HIDE_GRAPH1:

                Intent intent2 = new Intent(getContext(), PopupGraphActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_CREATE_REPORT:
                Toast.makeText(getContext(), "Relatório de análise salvo com sucesso", Toast.LENGTH_SHORT).show();
                break; 
        }
    }
    
    private class AsyncInsert extends AsyncTask<Void, Void, Void>  {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            waterReport.setWat_souName("aaaaa");

        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase.waterReportDao().insert(waterReport);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getContext(), "Amostra inserida", Toast.LENGTH_SHORT).show();

        }
    }

}
