package br.com.ufersa.qwater.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import br.com.ufersa.qwater.R;

public class PopupGraphActivity extends AppCompatActivity {

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_graph);

        //ajusta o tamanho para parecer uma popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.95),(int)(height*.6));



        graph = findViewById(R.id.GRAPH);
        addPointIntoGraph(2, 5);
        addPointIntoGraph(5, 10);
        addLines();

    }
    private void addPointIntoGraph(double x, double y){
        adjustGraph();

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(x, y)
        });
        graph.addSeries(series);
        series.setShape(PointsGraphSeries.Shape.POINT);
        series.setSize(2);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(PopupGraphActivity.this, "apertou no ponto: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void adjustGraph() {

        graph.setTitle("problemas de Infiltração por Sodicidade");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Relação de RASº");
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Condutividade Elétrica (dS/m) a 25ºC");
        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(false); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(false); // enables vertical zooming and scrolling

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-10);
        graph.getViewport().setMaxY(40);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-10);
        graph.getViewport().setMaxX(30);

    }

    private void addLines(){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 26),
                new DataPoint(26, 0)

        });
        graph.addSeries(series);// styling series

        series.setColor(Color.RED);
        series.setDrawDataPoints(false);
        series.setThickness(1);


        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 18),
                new DataPoint(22, 0)

        });
        graph.addSeries(series2);// styling series

        series2.setColor(Color.RED);
        series2.setDrawDataPoints(false);
        series2.setThickness(1);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(0, 10),
                new DataPoint(18, 0)

        });
        graph.addSeries(series3);// styling series

        series3.setColor(Color.RED);
        series3.setDrawDataPoints(false);
        series3.setThickness(1);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(5, 40),
                new DataPoint(5, 0)

        });
        graph.addSeries(series4);// styling series

        series4.setColor(Color.RED);
        series4.setDrawDataPoints(false);
        series4.setThickness(1);

        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(10, 40),
                new DataPoint(10, 0)

        });
        graph.addSeries(series5);// styling series

        series5.setColor(Color.RED);
        series5.setDrawDataPoints(false);
        series5.setThickness(1);

        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[]{

                new DataPoint(15, 40),
                new DataPoint(15, 0)

        });
        graph.addSeries(series6);// styling series

        series6.setColor(Color.RED);
        series6.setDrawDataPoints(false);
        series6.setThickness(1);
    }
}
