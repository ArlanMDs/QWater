package br.com.ufersa.qwater.activities;

import android.arch.persistence.room.Room;
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
import br.com.ufersa.qwater.beans.WaterSource;
import br.com.ufersa.qwater.database.AppDatabase;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

public class ListWaterSourceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_source);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //prepara o bd
        appDatabase = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "appDatabase.db").build();

        //busca a lista de fontes de água
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                final List<WaterSource> waterSources = appDatabase.waterSourceDao().getAll();

                // tive que criar essa outra thread pois aparecia o seguinte erro:
                // “Only the original thread that created a view hierarchy can touch its views.”
                // https://stackoverflow.com/questions/5161951/android-only-the-original-thread-that-created-a-view-hierarchy-can-touch-its-vi
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //insere a lista no recyclerview
                        adapter = new WaterSourceAdapter(waterSources);
                        recyclerView.setAdapter(adapter);
                    }
                });

            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListWaterSourceActivity.this, CreateWaterSourceActivity.class));

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListWaterSourceActivity.this, MainActivity.class));
    }

}
