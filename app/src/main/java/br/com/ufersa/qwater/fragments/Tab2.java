package br.com.ufersa.qwater.fragments;


import android.content.DialogInterface;
import android.content.Intent;
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
import br.com.ufersa.qwater.database.Update;
import br.com.ufersa.qwater.models.MyApp;
import br.com.ufersa.qwater.models.Report;

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button btnSARDetails, btnSalinityDetails, btnSaveReport;
    private TextView txtviewCorrectedSARResult, txtviewNormalSARResult, salinityResult, txtviewSARClassification, txtviewCEaValue;
    private int currentSARClassification, currentSalinityClassification;
    private Report report;
    private double x, y;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewsIds();
        //addLine();

    }



    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param report relatório recebido da tab1
     */
    public void updateData(Report report){

        this.report = report;

        categorizeSAR(report.getCorrectedSAR());
        categorizeSalinity(report.getCea());

        //arredenda os valores para 3 casas decimais
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(2);
        format.setMaximumIntegerDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        report.setNormalSAR(Double.valueOf(format.format(report.getNormalSAR())));
        report.setCorrectedSAR(Double.valueOf(format.format(report.getCorrectedSAR())));

        //insere os valores nos campos de texto
        showNormalSAR(report.getNormalSAR());
        showCorrectedSAR(report.getCorrectedSAR());

        showSalinityClassification(report.getCea());


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
        builder.setTitle(getString(R.string.classificacao)+ " "+ classificacao)
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

        txtviewCorrectedSARResult = (TextView) view.findViewById(R.id.txtviewCorrectedSARResult);
        txtviewNormalSARResult = (TextView) view.findViewById(R.id.txtviewNormalSARResult);
        txtviewSARClassification = (TextView) view.findViewById(R.id.txtviewSARClassification);
        txtviewCEaValue = (TextView) view.findViewById(R.id.txtviewCEaValue);
        salinityResult = (TextView) view.findViewById(R.id.txtviewSalinityResults);

        btnSARDetails = (Button) view.findViewById(R.id.buttonSARDetails);
        btnSARDetails.setOnClickListener(this);

        btnSalinityDetails = (Button) view.findViewById(R.id.buttonSalinityDetails);
        btnSalinityDetails.setOnClickListener(this);

        btnSaveReport = (Button) view.findViewById(R.id.buttonSaveReport);
        btnSaveReport.setOnClickListener(this);

        Button showHideGraph1 = (Button) view.findViewById(R.id.SHOW_HIDE_GRAPH1);
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

            case R.id.buttonSARDetails:
                showInfoDialogue("S" + currentSARClassification);

                break;
            case R.id.buttonSalinityDetails:
                showInfoDialogue("C" + currentSalinityClassification);

                break;
            case R.id.buttonSaveReport:

                if (new Update().addReport(report))
                    Toast.makeText(MyApp.getContext(), "Relatório salvo com sucesso!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MyApp.getContext(), "Erro ao inserir relatório", Toast.LENGTH_SHORT).show();

                break;
            case R.id.SHOW_HIDE_GRAPH1:

                /*if(graph.getVisibility() == View.GONE)
                    graph.setVisibility(View.VISIBLE);
                else
                    graph.setVisibility(View.GONE);
                */
                Intent intent = new Intent(getContext(), PopupGraphActivity.class);
                startActivity(intent);
                break;

        }
    }
}
