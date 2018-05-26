package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Create;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsIds();
        new Create().createTable();

    }
    private void findViewsIds() {
        Button newTest = findViewById(R.id.BUTTON_TABHOST_ACTIVITY);
        newTest.setOnClickListener(this);

        Button savedReports = findViewById(R.id.BUTTON_REPORT_DETAILS_ACTIVITY);
        savedReports.setOnClickListener(this);

        Button waterSource = findViewById(R.id.BUTTON_WATER_SOURCE_ACTIVITY);
        waterSource.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.BUTTON_TABHOST_ACTIVITY:
                intent = new Intent(MainActivity.this, TabHostActivity.class);
                startActivity(intent);
                break;
            case R.id.BUTTON_REPORT_DETAILS_ACTIVITY:
                intent = new Intent(MainActivity.this, StoredWaterSamplesActivity.class);
                startActivity(intent);
                break;
            case R.id.BUTTON_WATER_SOURCE_ACTIVITY:
                intent = new Intent(MainActivity.this, WaterSourceActivity.class);
                startActivity(intent);
                break;
        }
    }
/*
    @Override
    protected void onStop() {
        Toast.makeText(this, "fechando DB...", Toast.LENGTH_SHORT).show();

        //Lembre-se de fechar o DB quando fechar o app, e certifique-se de que o mesmo
        // não está em uso antes de chamar  MainDB.getInstancia().close();
        MainDB.getInstance().close();

        super.onStop();
    }
test
*/
}
