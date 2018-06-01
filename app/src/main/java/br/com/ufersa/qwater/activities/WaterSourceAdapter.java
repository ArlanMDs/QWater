package br.com.ufersa.qwater.activities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterSource;

class WaterSourceAdapter extends RecyclerView.Adapter<WaterSourceAdapter.ViewHolder> {

    List<WaterSource> waterSources;

    public WaterSourceAdapter(List<WaterSource> waterSources) {
        this.waterSources = waterSources;
    }

    @NonNull
    @Override
    public WaterSourceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.water_source_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaterSourceAdapter.ViewHolder holder, int position) {
        holder.sourceName.setText(waterSources.get(position).getSouName());
    }

    @Override
    public int getItemCount() {
        return waterSources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
       private TextView sourceName;

         ViewHolder(View itemView){
            super(itemView);
            sourceName = itemView.findViewById(R.id.TEXTVIEW_WATERSOURCE_NAME);
        }
    }
}
