package br.com.ufersa.qwater.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.ReportDetailsActivity;
import br.com.ufersa.qwater.beans.Report;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private final List<Report> reports;
    private final Context context;

    public ReportAdapter(Context context, List<Report> reports) {
        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row, parent, false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, final int position) {

        holder.reportSourceName.setText(reports.get(position).getSouName());
        holder.reportDate.setText(longToDateFormat(reports.get(position).getDate()));
        // o listener passa o ID da amostra selecionada para a activity de detalhes
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ReportDetailsActivity.class);
                intent.putExtra("report", reports.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//app deu crash, o log do erro pediu essa flag.
                context.startActivity(intent);
            }
        });
    }

    private String longToDateFormat(long longValue){

        return new SimpleDateFormat("dd/MM/yyyy").format(new Date(longValue));

    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView reportSourceName;
        private final TextView reportDate;
        final ConstraintLayout parentLayout;

        ViewHolder(View itemView){
            super(itemView);
            reportSourceName = itemView.findViewById(R.id.TEXTVIEW_REPORT_SOURCE_NAME);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_REPORT_ROW);
            reportDate = itemView.findViewById(R.id.TEXTVIEW_REPORT_DATE);

        }
    }
}
