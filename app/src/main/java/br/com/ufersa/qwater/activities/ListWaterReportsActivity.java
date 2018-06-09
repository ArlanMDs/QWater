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
import br.com.ufersa.qwater.beans.WaterReport;
import br.com.ufersa.qwater.database.AppDatabase;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

public class ListWaterReportsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_water_reports);
        Toolbar toolbar = findViewById(R.id.LIST_WATER_REPORTS_TOOLBAR);//o toolbar está desabilitado no manifest
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //prepara o bd
        appDatabase = AppDatabase.getInstance(ListWaterReportsActivity.this);

        ListWaterReportsActivity.AsyncRead asyncRead = new ListWaterReportsActivity.AsyncRead();
        asyncRead.execute();

        fab = findViewById(R.id.FAB_LIST_WATER_REPORTS);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListWaterReportsActivity.this, CreateWaterReportActivity.class));

            }
        });
    }

    private class AsyncRead extends AsyncTask<Void, Void, List<WaterReport>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<WaterReport> doInBackground(Void... voids) {

            return appDatabase.waterReportDao().getAll();
        }

        @Override
        protected void onPostExecute(List<WaterReport> waterReports) {
            adapter = new WaterReportAdapter(getApplicationContext(),waterReports);
            recyclerView.setAdapter(adapter);

        }
    }


}
