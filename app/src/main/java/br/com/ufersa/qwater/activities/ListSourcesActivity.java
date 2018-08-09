package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.SourceAdapter;
import br.com.ufersa.qwater.beans.Source;
import br.com.ufersa.qwater.database.AppDatabase;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

public class ListSourcesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sources);

        recyclerView = findViewById(R.id.WATER_SOURCE_RECYCLER_VIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //prepara o bd
        appDatabase = AppDatabase.getInstance(ListSourcesActivity.this);

        AsyncRead asyncRead = new AsyncRead();
        asyncRead.execute();

    }

    private class AsyncRead extends AsyncTask<Void, Void, List<Source>>  {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Source> doInBackground(Void... voids) {

            return appDatabase.sourceDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Source> sources) {

            RecyclerView.Adapter adapter = new SourceAdapter(getCodeOfIncomingIntent(), ListSourcesActivity.this, sources);
            recyclerView.setAdapter(adapter);

        }
    }
    private int getCodeOfIncomingIntent(){
        if(getIntent().hasExtra("callingActivity")) {
            // Caso a activity que chamou seja a analize, esconde o fab para que não vá para a activity de criar
            // E troca o título da activity
            return getIntent().getIntExtra("callingActivity",0);
        }
        else{
            return 0;//TODO aqui é se quem chama é a mainactivity
        }
    }

    //para nao voltar para a activity de coordenadas
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListSourcesActivity.this, MainActivity.class));
    }

}