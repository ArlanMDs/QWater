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

import static br.com.ufersa.qwater.util.Flags.CALLING_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.MAIN_ACTIVITY;

//referência recyclerView https://www.youtube.com/watch?v=CTBiwKlO5IU&t=2762s

/**
 *
 * Esta activity existe para exibir a lista de fontes quando vai salvar um relatório, pois não deu certo usar o fragmento de lista da main
 *
 *
 */
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

        new AsyncRead().execute();

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
        if(getIntent().hasExtra(CALLING_ACTIVITY)) {
            // Caso a activity que chamou seja a analize, a intent tem esse extra
            // E troca o título da activity
            return getIntent().getIntExtra(CALLING_ACTIVITY,MAIN_ACTIVITY);// em caso de vazio, volta pra main
        }
        else
            return MAIN_ACTIVITY;
    }

    //para nao voltar para a activity de coordenadas
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListSourcesActivity.this, MainActivity.class));
    }

}
