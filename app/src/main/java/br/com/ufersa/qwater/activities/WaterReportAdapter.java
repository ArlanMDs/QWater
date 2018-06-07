package br.com.ufersa.qwater.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

class WaterReportAdapter extends RecyclerView.Adapter<WaterReportAdapter.ViewHolder>{

    List<WaterReport> waterReports;
    private Context context;

    public WaterReportAdapter(Context context, List<WaterReport> waterReports) {
        this.waterReports = waterReports;
        this.context = context;
    }

    @NonNull
    @Override
    public WaterReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_report_row, parent, false);
        return new WaterReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterReportAdapter.ViewHolder holder, final int position) {
        //holder.reportSourceName.setText(String.valueOf(waterReports.get(position).getWatCorrectedSar()));
        holder.reportSourceName.setText("Açude do caz");

        // o listener passa o ID da amostra selecionada para a activity de detalhes
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, WaterReportDetailsActivity.class);
                intent.putExtra("waterReport",waterReports.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//app deu crash, o log do erro pediu essa flag.
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return waterReports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView reportSourceName;
        ConstraintLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            reportSourceName = itemView.findViewById(R.id.TEXTVIEW_REPORT_SOURCE_NAME);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_REPORT_ROW);

        }
    }
}
