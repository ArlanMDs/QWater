package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterSource;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

public class ListWaterSourceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FloatingActionButton fab;
    private ArrayList<WaterSource> waterSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_source);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        waterSources = new ArrayList<>();

        for (int i = 0; i<10;i++){
            WaterSource waterSource = new WaterSource();
            waterSource.setSouName("Fonte #"+i);
            waterSource.setSouLatitude(i);
            waterSource.setSouLongitude(i);

            waterSources.add(waterSource);
        }



        adapter = new WaterSourceAdapter(waterSources);

        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListWaterSourceActivity.this, CreateWaterSourceActivity.class));

            }
        });
    }

}
