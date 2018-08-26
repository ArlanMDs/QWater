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
import br.com.ufersa.qwater.activities.SourceDetailsActivity;
import br.com.ufersa.qwater.beans.Source;

import static android.app.Activity.RESULT_OK;
import static br.com.ufersa.qwater.util.Flags.MAIN_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.SAVE_REPORT_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.SOURCE;
import static br.com.ufersa.qwater.util.Flags.SOURCE_ID;
import static br.com.ufersa.qwater.util.Flags.SOURCE_NAME;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private final List<Source> sources;
    private final Context context;
    private final int callingActivity;
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

        if (callingActivity == SAVE_REPORT_ACTIVITY){//se não é a main, é a save report! então é necessário ativar o listener que retorna o nome e id
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(SOURCE_ID, sources.get(position).getId());
                    intent.putExtra(SOURCE_NAME, sources.get(position).getName());
                    ((Activity) context).setResult(RESULT_OK, intent);
                    ((Activity) context).finish();

                }
            });
        }else if( callingActivity == MAIN_ACTIVITY){
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            Intent intent = new Intent(context, SourceDetailsActivity.class)
                    .putExtra(SOURCE, sources.get(position))
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
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
