package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.ufersa.qwater.R;

//TODO sempre que a lista é exibida está sendo feito um acesso ao banco,
// o delete poderia ser feito sem precisar acessar novamente o banco, bastaria apenas remover a coluna da lista após o delete, sem usar o refresh na activity
public class StoredWaterSamplesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stored_water_samples);
        listReports();
    }

    private void listReports() {
        ListView listView = findViewById(R.id.displayListView);


        // Create The Adapter with passing ArrayList and Activity as parameter
        //TODO Tratar cursor vazio
        // Set The Adapter

        // register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Intent intent = new Intent(StoredWaterSamplesActivity.this, WaterSamplesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

}
