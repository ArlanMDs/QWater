package br.com.ufersa.qwater.models;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import br.com.ufersa.qwater.R;

/**
 * Created by Arlan on 27-Nov-17.
 * http://blog.alura.com.br/personalizando-uma-listview-no-android/
 */

public class CustomReportAdapter extends BaseAdapter {

    private final List<Report> reports;
    private final Activity activity;

    public CustomReportAdapter(List<Report> reports, Activity activity) {
        this.reports = reports;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return reports.size();
    }

    @Override
    public Object getItem(int position) {
        return reports.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reports.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.display_report_row, parent, false);
        Report report = reports.get(position);
        //TODO implementar view holder pattern
        //pegando as referências das Views
        TextView id = view.findViewById(R.id.id);
        TextView normalSAR = view.findViewById(R.id.normalSAR);
        TextView correctedSAR = view.findViewById(R.id.correctedSAR);
        TextView createdAt = view.findViewById(R.id.createdAt);

        //populando as Views
        id.setText(String.valueOf(report.getId()));
        normalSAR.setText(String.valueOf(report.getNormalSAR()));
        correctedSAR.setText(String.valueOf(report.getCorrectedSAR()));

         try {
             createdAt.setText(formatDate(report.getCreatedAt()));
         }catch (Exception e){
             e.printStackTrace();
             createdAt.setText("-1");
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
