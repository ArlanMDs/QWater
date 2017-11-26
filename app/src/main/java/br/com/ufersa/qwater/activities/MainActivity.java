package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.MainDB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsIds();
    }

    private void findViewsIds() {
        Button newTest = (Button)findViewById(R.id.newTest);
        newTest.setOnClickListener(this);
        Button savedReports = (Button)findViewById(R.id.savedReports);
        savedReports.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.newTest:
                intent = new Intent(MainActivity.this, TabHostActivity.class);
                startActivity(intent);
                break;
            case R.id.savedReports:
                intent = new Intent(MainActivity.this, SavedReportsActivity.class);
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
*/
}
