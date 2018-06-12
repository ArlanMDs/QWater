package br.com.ufersa.qwater.activities;

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
import br.com.ufersa.qwater.beans.WaterSource;

import static android.app.Activity.RESULT_OK;

class WaterSourceAdapter extends RecyclerView.Adapter<WaterSourceAdapter.ViewHolder> {

    List<WaterSource> waterSources;
    private Context context;
    private int callingActivity;
    private final static int REQUEST_CODE_ANALISE_ACTIVITY = 1;
    /*
        precisei criar uma maneira para diferenciar a activity que cria o adapter, para assim poder atribuir diferentes funções ao clicar em uma row
        tentei o context, mas por algum motivo deu errado...
        AnalizeWaterReportActivity: 1

     */
    public WaterSourceAdapter(int callingActivity, Context context, List<WaterSource> waterSources) {
        this.waterSources = waterSources;
        this.context = context;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public WaterSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_source_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterSourceAdapter.ViewHolder holder, final int position) {
        holder.sourceName.setText(waterSources.get(position).getSouName());

        if (callingActivity == REQUEST_CODE_ANALISE_ACTIVITY){
            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("sourceID", waterSources.get(position).getSouID());
                    intent.putExtra("sourceName", waterSources.get(position).getSouName());
                    ((Activity) context).setResult(RESULT_OK, intent);
                    ((Activity) context).finish();

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return waterSources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView sourceName;
        ConstraintLayout parentLayout;

         ViewHolder(View itemView){
            super(itemView);
            sourceName = itemView.findViewById(R.id.TEXTVIEW_WATERSOURCE_NAME);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_SOURCE_ROW);
        }
    }
}
