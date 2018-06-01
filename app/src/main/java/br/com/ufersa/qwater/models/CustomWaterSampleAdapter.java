package br.com.ufersa.qwater.models;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Calendar;
import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterSample;

/**
 * Created by Arlan on 27-Nov-17.
 * http://blog.alura.com.br/personalizando-uma-listview-no-android/
 */

public class CustomWaterSampleAdapter extends BaseAdapter {

    private final List<WaterSample> waterSamples;
    private final Activity activity;

    public CustomWaterSampleAdapter(List<WaterSample> waterSamples, Activity activity) {
        this.waterSamples = waterSamples;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return waterSamples.size();
    }

    @Override
    public Object getItem(int position) {
        return waterSamples.get(position);
    }

    @Override
    public long getItemId(int position) {
        return waterSamples.get(position).getWatID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.water_sample_row, parent, false);
        WaterSample waterSample = waterSamples.get(position);
        //TODO implementar view holder pattern
        //pegando as referências das Views
       // TextView id = view.findViewById(R.id.id);
       // TextView normalSAR = view.findViewById(R.id.normalSAR);
       // TextView correctedSAR = view.findViewById(R.id.correctedSAR);
       // TextView createdAt = view.findViewById(R.id.createdAt);

        //populando as Views
       //id.setText(String.valueOf(waterSample.getWatID()));
       // normalSAR.setText(String.valueOf(waterSample.getWatNormalSar()));
       // correctedSAR.setText(String.valueOf(waterSample.getWatCorrectedSar()));

         try {
        //     createdAt.setText(formatDate(waterSample.getWatCreatedAt()));
         }catch (Exception e){
             e.printStackTrace();
        //     createdAt.setText("-1");
         }
        return view;
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
}
