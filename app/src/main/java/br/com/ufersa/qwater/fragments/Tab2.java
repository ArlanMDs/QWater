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


import br.com.ufersa.qwater.MyApp;
import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Update;
import br.com.ufersa.qwater.models.Report;

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button btnSARDetails, btnSalinityDetails, btnSaveReport;
    private TextView txtviewCorrectedSARResult, txtviewNormalSARResult, salinityResult, txtviewSARClassification, txtviewCEaValue;
    private int currentSARClassification, currentSalinityClassification;
    private GraphView graph;
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

    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param report relatório recebido da tab1
     */
    public void updateData(Report report){

        this.report = report;

        categorizeSAR(report.getCorrectedSAR());
        categorizeSalinity(report.getCea());

        showNormalSAR(report.getNormalSAR());
        showCorrectedSARClassification(report.getCorrectedSAR());
        showSalinityClassification(report.getCea());

        addPointIntoGraph(report.getCea(), report.getCorrectedSAR());

    }

    private void addPointIntoGraph(double x, double y){
        adjustGraph();

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

    private void adjustGraph() {
        graph.setTitle("problemas de Infiltração por Sodicidade");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Relação de RASº");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Condutividade Elétrica (dS/m) a 25ºC");
       // graph.getViewport().setScalable(true); // enables horizontal scrolling
        graph.getViewport().setScalableY(true); // enables vertical scrolling
        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(30);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(10);

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
    private void showCorrectedSARClassification(Double correctedSAR) {

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
        graph = (GraphView) view.findViewById(R.id.GRAPH1);

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

                if(graph.getVisibility() == View.GONE)
                    graph.setVisibility(View.VISIBLE);
                else
                    graph.setVisibility(View.GONE);

                break;

        }
    }
}
