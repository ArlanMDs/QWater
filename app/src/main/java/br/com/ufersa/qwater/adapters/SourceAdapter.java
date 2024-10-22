package br.com.ufersa.qwater.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private final List<Source> sources;
    private final Context context;
    private final int callingActivity;

    public SourceAdapter(int callingActivity, Context context, List<Source> sources) {
        this.sources = sources;
        this.context = context;
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.source_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Source source = sources.get(position);
        holder.bind(source);
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView sourceName;
        final ConstraintLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            sourceName = itemView.findViewById(R.id.TEXTVIEW_WATERSOURCE_NAME);
            parentLayout = itemView.findViewById(R.id.LAYOUT_WATER_SOURCE_ROW);
        }

        void bind(Source source) {
            sourceName.setText(source.getName());
            parentLayout.setOnClickListener(v -> handleClick(source));
        }

        private void handleClick(Source source) {
            if (callingActivity == SAVE_REPORT_ACTIVITY) {
                Intent intent = new Intent();
                intent.putExtra(SOURCE_ID, source.getId());
                intent.putExtra(SOURCE_NAME, source.getName());
                ((Activity) context).setResult(RESULT_OK, intent);
                ((Activity) context).finish();
            } else if (callingActivity == MAIN_ACTIVITY) {
                Intent intent = new Intent(context, SourceDetailsActivity.class)
                        .putExtra(SOURCE, source);
                context.startActivity(intent);
            }
        }
    }
}

