package br.com.ufersa.qwater.adapters;

import android.app.Activity;
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
import br.com.ufersa.qwater.beans.Source;

import static android.app.Activity.RESULT_OK;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private final List<Source> sources;
    private final Context context;
    private final int callingActivity;
    private final static int REQUEST_CODE_ANALISE_ACTIVITY = 1;
    /*
        precisei criar uma maneira para diferenciar a activity que cria o adapter, para assim poder atribuir diferentes funções ao clicar em uma row
        tentei o context, mas por algum motivo deu errado...
        AnalyzeWaterReportActivity: 1

     */
    public SourceAdapter(int callingActivity, Context context, List<Source> sources) {
        this.sources = sources;
        this.context = context;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public SourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SourceAdapter.ViewHolder holder, final int position) {
        holder.sourceName.setText(sources.get(position).getName());

        if (callingActivity == REQUEST_CODE_ANALISE_ACTIVITY){
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("sourceID", sources.get(position).getId());
                    intent.putExtra("sourceName", sources.get(position).getName());
                    ((Activity) context).setResult(RESULT_OK, intent);
                    ((Activity) context).finish();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView sourceName;
        final ConstraintLayout parentLayout;

         ViewHolder(View itemView){
            super(itemView);
            sourceName = itemView.findViewById(R.id.TEXTVIEW_WATERSOURCE_NAME);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_SOURCE_ROW);
        }
    }
}
