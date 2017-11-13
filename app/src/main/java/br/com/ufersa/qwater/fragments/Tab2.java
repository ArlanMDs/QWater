package br.com.ufersa.qwater.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.models.Report;

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button btnRASDetails, btnSalinityDetails;
    private TextView txtviewRASResult, salinityResult, txtviewRASClassification;
    private int currentRASClassification, currentSalinityClassification;
    private GraphView graph;

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
        //adjustGraph();

    }

    private void adjustGraph() {
        addScrolling();
        graph.setTitle("Classificação de água para irrigação");

        // set manual X bounds
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.5);
        graph.getViewport().setMaxX(3.5);

        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(3.5);
        graph.getViewport().setMaxY(8);
    }

    private void addPointIntoGraph(double x, double y){

        /*para o gráfico da figura 1, página 55 do livro "Manejo de salinidade na agricultura: estudos básicos e aplicados,
         a CEa é expressa em micro mhos/cm, a conversão é feita multiplicando o valor da CEa, expressa em dS/m, por 1000

         */
        x *= 1000;

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(x, y)
        });
        graph.addSeries(series);
        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setSize(7);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "apertou no ponto: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addLine(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {

                new DataPoint(0.15, 0),
                new DataPoint(3, 25)

        });
        graph.addSeries(series);// styling series

        series.setTitle("Random Curve 1");
        series.setColor(Color.RED);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(4);
        series.setThickness(2);

    }

    private void addScrolling(){
        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(25);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
    }

    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param report relatório recebido da tab1
     */
    public void updateData(Report report){

        categorizeRAS(report.getCorrectedRAS());
        categorizeSalinity(report.getCEa());

        showRASClassification(report.getCorrectedRAS());
        showSalinityClassification(report.getCEa());

        addPointIntoGraph(report.getCEa(), report.getCorrectedRAS());

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

    private void categorizeRAS(double correctedRAS) {

        if(correctedRAS < 10.0)
            currentRASClassification = 1;
        else if(correctedRAS >= 10.0 && correctedRAS < 18.0)
            currentRASClassification = 2;
        else if(correctedRAS >=18.0 && correctedRAS < 26.0)
            currentRASClassification = 3;
        else
            currentRASClassification = 4;
    }



    /**
     * mostra uma janela contendo informações em texto sobre a classificação do RAS ou salinidade
     * @param string RAS ou salinidade a ser classificada
     */
    public void showInfoDialogue(String string){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        int resourceId = getContext().getResources().getIdentifier(string, "string", getContext().getPackageName());
        builder.setTitle(getString(R.string.classificacao))
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

        salinityResult.setText(String.valueOf(currentSalinityClassification));
    }

    /**
     * classifica o valor do RAS entre intervalos
     * @param correctedRAS valor do RAS
     */
    private void showRASClassification(Double correctedRAS) {

        btnRASDetails.setVisibility(View.VISIBLE);

        txtviewRASResult.setText(String.valueOf(correctedRAS));
        txtviewRASClassification.setText(String.valueOf(currentRASClassification));

    }

    private void findViewsIds() {

        txtviewRASResult = (TextView)getView().findViewById(R.id.txtviewRASResult);
        txtviewRASClassification = (TextView)getView().findViewById(R.id.txtviewRASClassification);
        salinityResult = (TextView)getView().findViewById(R.id.txtviewSalinityResults);

        btnRASDetails = (Button)getView().findViewById(R.id.btnRASDetails);
        btnRASDetails.setOnClickListener(this);

        btnSalinityDetails = (Button)getView().findViewById(R.id.btnSalinityDetails);
        btnSalinityDetails.setOnClickListener(this);

        graph = (GraphView) getView().findViewById(R.id.dotgraph);


    }

    /**
     * manipula os clicks nos botões
     * @param v id do botão
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /*
            a concatenação é para que seja feito apelas um método para chamar a caixa de diálogo,
            pois ela usa uma string com "S" ou "C" juntamente com o número da classificação para
            buscar o valor em strings.xml e montar a tela de dialog.
             */

            case R.id.btnRASDetails:
                showInfoDialogue("S" + currentRASClassification);

                break;

            case R.id.btnSalinityDetails:
                showInfoDialogue("C" + currentSalinityClassification);

                break;
        }
    }
}
