package br.com.ufersa.qwater.activities;

import android.support.annotation.NonNull;
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

    public WaterReportAdapter(List<WaterReport> waterReports) {
        this.waterReports = waterReports;
    }

    @NonNull
    @Override
    public WaterReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_report_row, parent, false);
        return new WaterReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterReportAdapter.ViewHolder holder, int position) {
        holder.correctedSAR.setText(String.valueOf(waterReports.get(position).getWatCorrectedSar()));
    }

    @Override
    public int getItemCount() {
        return waterReports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView correctedSAR;

        ViewHolder(View itemView){
            super(itemView);
            correctedSAR = itemView.findViewById(R.id.TEXTVIEW_WATER_REPORT_CORRECTED_SAR);
        }
    }
}
