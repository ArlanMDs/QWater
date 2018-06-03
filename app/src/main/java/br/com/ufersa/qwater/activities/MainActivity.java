package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.ufersa.qwater.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsIds();

    }
    private void findViewsIds() {
        Button newTest = findViewById(R.id.BUTTON_TABHOST_ACTIVITY);
        newTest.setOnClickListener(this);

        Button savedReports = findViewById(R.id.BUTTON_LIST_WATERSAMPLES_ACTIVITY);
        savedReports.setOnClickListener(this);

        Button waterSource = findViewById(R.id.BUTTON_WATERSOURCE_ACTIVITY);
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
            case R.id.BUTTON_LIST_WATERSAMPLES_ACTIVITY:
                startActivity(new Intent(MainActivity.this, ListWaterReportsActivity.class));

                break;
            case R.id.BUTTON_WATERSOURCE_ACTIVITY:
                intent = new Intent(MainActivity.this, ListWaterSourcesActivity.class);
                startActivity(intent);
                break;
        }
    }

}
