package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.SourceAdapter;
import br.com.ufersa.qwater.beans.Source;
import br.com.ufersa.qwater.database.AppDatabase;

import static br.com.ufersa.qwater.util.Flags.CALLING_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.MAIN_ACTIVITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Esta activity existe para exibir a lista de fontes quando vai salvar um relatório,
 * pois não deu certo usar o fragmento de lista da main.
 */
public class ListSourcesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sources);

        recyclerView = findViewById(R.id.WATER_SOURCE_RECYCLER_VIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Prepara o banco de dados
        appDatabase = AppDatabase.getInstance(this);
        executorService = Executors.newSingleThreadExecutor();

        loadSources();
    }

    private void loadSources() {
        executorService.execute(() -> {
            List<Source> sources = appDatabase.sourceDao().getAll();

            runOnUiThread(() -> {
                SourceAdapter adapter = new SourceAdapter(getCodeOfIncomingIntent(), ListSourcesActivity.this, sources);
                recyclerView.setAdapter(adapter);
            });
        });
    }

    private int getCodeOfIncomingIntent() {
        if (getIntent().hasExtra(CALLING_ACTIVITY)) {
            return getIntent().getIntExtra(CALLING_ACTIVITY, MAIN_ACTIVITY);
        } else {
            return MAIN_ACTIVITY;
        }
    }

    // Para não voltar para a activity de coordenadas
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListSourcesActivity.this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Para encerrar o executor quando não for mais necessário
    }
}
