package br.com.ufersa.qwater.adapters;

import static br.com.ufersa.qwater.util.Flags.REPORT;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.ReportDetailsActivity;
import br.com.ufersa.qwater.beans.Report;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {

    private final List<Report> reports;
    private final Context context;

    public ReportAdapter(Context context, List<Report> reports) {
        this.reports = reports;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Report report = reports.get(position);
        holder.bind(report);
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView reportSourceName;
        private final TextView reportDate;
        final ConstraintLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            reportSourceName = itemView.findViewById(R.id.TEXTVIEW_REPORT_SOURCE_NAME);
            reportDate = itemView.findViewById(R.id.TEXTVIEW_REPORT_DATE);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_REPORT_ROW);
        }

        void bind(Report report) {
            reportSourceName.setText(report.getSouName());
            reportDate.setText(longToDateFormat(report.getDate()));
            parentLayout.setOnClickListener(v -> openReportDetails(report));
        }

        private void openReportDetails(Report report) {
            Intent intent = new Intent(context, ReportDetailsActivity.class);
            intent.putExtra(REPORT, report);
            context.startActivity(intent);
        }

        private String longToDateFormat(long longValue) {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date(longValue));
        }
    }
}
