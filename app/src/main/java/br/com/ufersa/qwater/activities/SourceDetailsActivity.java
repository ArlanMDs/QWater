package br.com.ufersa.qwater.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Source;
import br.com.ufersa.qwater.database.AppDatabase;

import static br.com.ufersa.qwater.util.Flags.DELETE_SOURCE;
import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.SOURCE;
import static br.com.ufersa.qwater.util.Flags.UPDATE_SOURCE;

public class SourceDetailsActivity extends AppCompatActivity {

    private TextView sourceName, sourceType, sourceLatitude, sourceLongitude;
    private AppDatabase appDatabase;
    private Source source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_details);

        initiate();
        getIncomingIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.source_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch(item.getItemId()){

            case R.id.action_edit_source:

                intent = new Intent(SourceDetailsActivity.this, MainActivity.class)
                        .putExtra(GOING_TO, UPDATE_SOURCE)
                        .putExtra(SOURCE,source);

                startActivity(intent);

                return true;

            case R.id.action_delete_source:

                new AlertDialog.Builder(this)
                        .setTitle(R.string.deletando_fonte)
                        .setMessage(R.string.certeza_deletar_fonte)
                        .setPositiveButton(R.string.sim,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteSource();
                                    }
                                })
                        .setNegativeButton(R.string.nao, null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void initiate() {
        sourceName = findViewById(R.id.SOURCE_DETAILS_NAME);
        sourceType = findViewById(R.id.SOURCE_DETAILS_TYPE);
        sourceLatitude = findViewById(R.id.SOURCE_DETAILS_LATITUDE);
        sourceLongitude = findViewById(R.id.SOURCE_DETAILS_LONGITUDE);
        Toolbar mTopToolbar = findViewById(R.id.SOURCE_DETAILS_TOOLBAR);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setNavigationIcon(R.drawable.action_navigation_arrow);
        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // prepara o bd
        appDatabase = AppDatabase.getInstance(SourceDetailsActivity.this);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(SOURCE)) {

            source = getIntent().getParcelableExtra(SOURCE);
            updateUI();
        }
    }

    private void updateUI() {

        sourceName.setText(source.getName());
        sourceType.setText(source.getType());
        sourceLatitude.setText(source.getLatitude());
        sourceLongitude.setText(source.getLongitude());

    }

    private void deleteSource() {
        try {
            new SourceDetailsActivity.AsyncDelete().execute();
            // no post execute é direcionado para o fragment de listar fontes
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, R.string.erro_deletar_fonte, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SourceDetailsActivity.this, MainActivity.class));
        }
    }

    private class AsyncDelete extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.sourceDao().delete(source);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(SourceDetailsActivity.this, R.string.fonte_deletada, Toast.LENGTH_SHORT).show();
            // se eu der somente um finish(), a lista não atualiza...
            Intent intent = new Intent(SourceDetailsActivity.this, MainActivity.class);
            intent.putExtra(GOING_TO, DELETE_SOURCE);
            startActivity(intent);
        }
    }
}
