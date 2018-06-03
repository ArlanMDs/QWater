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
import br.com.ufersa.qwater.activities.PopupGraphActivity;
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.database.AppDatabase;

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button btnSARDetails, btnSalinityDetails, btnSaveReport;
    private TextView txtviewCorrectedSARResult, txtviewNormalSARResult, salinityResult, txtviewSARClassification, txtviewCEaValue;
    private int currentSARClassification, currentSalinityClassification;
    private WaterReport waterReport;
    private AppDatabase appDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appDatabase = AppDatabase.getInstance(getContext());
        findViewsIds();

    }

    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param waterReport relatório recebido da tab1
     */
    public void updateData(WaterReport waterReport){

        waterReport.setWatNormalSar(formatFractionDigits(waterReport.getWatNormalSar()));
        waterReport.setWatCorrectedSar(formatFractionDigits(waterReport.getWatCorrectedSar()));
        // A classificação e armazenamento está sendo feito após o arredondamento das casas decimais!
        this.waterReport = waterReport;
        categorizeSAR(waterReport.getWatCorrectedSar());
        categorizeSalinity(waterReport.getWatCea());
        updateTextViews();

    }

    private void updateTextViews(){
        //insere os valores nos campos de texto
        showNormalSAR(waterReport.getWatNormalSar());
        showCorrectedSAR(waterReport.getWatCorrectedSar());

        showSalinityClassification(waterReport.getWatCea());
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

    /**
     * classifica a salinidade de acordo com os intervalos dados
     * @param CEa parametro de classificação
     */
    private void showSalinityClassification(double CEa) {

        btnSalinityDetails.setVisibility(View.VISIBLE);

        txtviewCEaValue.setText(String.valueOf(CEa));
        salinityResult.setText("C" + String.valueOf(currentSalinityClassification));
    }

    private void showNormalSAR(double nomalSAR){
        txtviewNormalSARResult.setText(String.valueOf(nomalSAR));
    }

    /**
     * classifica o valor do RAS entre intervalos
     * @param correctedSAR valor do RAS
     */
    private void showCorrectedSAR(Double correctedSAR) {

        btnSARDetails.setVisibility(View.VISIBLE);

        txtviewCorrectedSARResult.setText(String.valueOf(correctedSAR));
        txtviewSARClassification.setText("S" + String.valueOf(currentSARClassification));
    }

    private void findViewsIds() {
        View view = getView();

        txtviewCorrectedSARResult = view.findViewById(R.id.TEXTVIEW_CORRECTED_SAR_RESULT);
        txtviewNormalSARResult = view.findViewById(R.id.txtviewNormalSARResult);
        txtviewSARClassification = view.findViewById(R.id.TEXTVIEW_SAR_CLASSIFICATION);
        txtviewCEaValue = view.findViewById(R.id.txtviewCEaValue);
        salinityResult = view.findViewById(R.id.txtviewSalinityResults);

        btnSARDetails = view.findViewById(R.id.BUTTON_SAR_DETAILS);
        btnSARDetails.setOnClickListener(this);

        btnSalinityDetails = view.findViewById(R.id.buttonSalinityDetails);
        btnSalinityDetails.setOnClickListener(this);

        btnSaveReport = view.findViewById(R.id.BUTTON_SAVE_WATER_REPORT);
        btnSaveReport.setOnClickListener(this);

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

            case R.id.BUTTON_SAR_DETAILS:
                showInfoDialogue("S" + currentSARClassification);

                break;
            case R.id.buttonSalinityDetails:
                showInfoDialogue("C" + currentSalinityClassification);

                break;
            case R.id.BUTTON_SAVE_WATER_REPORT:
                //Abrir uma nova activity, igual a about, perguntar em qual
                new AsyncInsert().execute();

                break;
            case R.id.SHOW_HIDE_GRAPH1:

                Intent intent = new Intent(getContext(), PopupGraphActivity.class);
                startActivity(intent);
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
