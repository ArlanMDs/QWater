package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterSample;
import br.com.ufersa.qwater.database.AppDatabase;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

public class ListWaterSamplesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_water_samples);
        Toolbar toolbar = findViewById(R.id.LIST_WATERSAMPLES_TOOLBAR);//o toolbar está desabilitado no manifest
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //prepara o bd
        appDatabase = AppDatabase.getInstance(ListWaterSamplesActivity.this);

        ListWaterSamplesActivity.AsyncRead asyncRead = new ListWaterSamplesActivity.AsyncRead();
        asyncRead.execute();

        fab = findViewById(R.id.FAB_LIST_WATERSAMPLES);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListWaterSamplesActivity.this, TabHostActivity.class));

            }
        });
    }

    private class AsyncRead extends AsyncTask<Void, Void, List<WaterSample>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<WaterSample> doInBackground(Void... voids) {

            return appDatabase.waterSampleDao().getAll();
        }

        @Override
        protected void onPostExecute(List<WaterSample> waterSamples) {
            adapter = new WaterSampleAdapter(waterSamples);
            recyclerView.setAdapter(adapter);

        }
    }


}
