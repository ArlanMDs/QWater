package br.com.ufersa.qwater.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterSample;

class WaterSampleAdapter extends RecyclerView.Adapter<WaterSampleAdapter.ViewHolder>{

    List<WaterSample> waterSamples;

    public WaterSampleAdapter(List<WaterSample> waterSamples) {
        this.waterSamples = waterSamples;
    }

    @NonNull
    @Override
    public WaterSampleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_sample_row, parent, false);
        return new WaterSampleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterSampleAdapter.ViewHolder holder, int position) {
        holder.correctedSAR.setText(String.valueOf(waterSamples.get(position).getWatCorrectedSar()));
    }

    @Override
    public int getItemCount() {
        return waterSamples.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView correctedSAR;

        ViewHolder(View itemView){
            super(itemView);
            correctedSAR = itemView.findViewById(R.id.TEXTVIEW_WATERSAMPLE_CORRECTEDSAR);
        }
    }
}
