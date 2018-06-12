package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class CreateWaterReportActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText edtCea, edtCa, edtMg, edtK, edtNa, edtCo3, edtHco3, edtCl, edtPH;
    private Button analyzeButton;
    private Spinner spinnerCEa, spinnerMolecules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_water_report);

        initiate();

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(CreateWaterReportActivity.this, AnalizeWaterReportActivity.class);
            intent.putExtra("waterReport", generateReport());
            startActivity(intent);


            }
        });
    }

    private WaterReport generateReport() {
        WaterReport waterReport = new WaterReport();

        if(edtMg.getText().length() > 0)
            waterReport.setWatMg(parseToDouble(edtMg.getText().toString()));
        else
            waterReport.setWatMg(0);

        if(edtK.getText().length() > 0)
            waterReport.setWatK(parseToDouble(edtK.getText().toString()));
        else
            waterReport.setWatK(0);

        if(edtCa.getText().length() > 0)
            waterReport.setWatCa(parseToDouble(edtCa.getText().toString()));
        else
            waterReport.setWatCa(0);

        if(edtNa.getText().length() > 0)
            waterReport.setWatNa(parseToDouble(edtNa.getText().toString()));
        else
            waterReport.setWatNa(0);

        if(edtCl.getText().length() > 0)
            waterReport.setWatCl(parseToDouble(edtCl.getText().toString()));
        else
            waterReport.setWatCl(0);

        if(edtCo3.getText().length() > 0)
            waterReport.setWatCo3(parseToDouble(edtCo3.getText().toString()));
        else
            waterReport.setWatCo3(0);

        if(edtHco3.getText().length() > 0)
            waterReport.setWatHco3(parseToDouble(edtHco3.getText().toString()));
        else
            waterReport.setWatHco3(0);

        if(edtPH.getText().length() > 0)
            waterReport.setWatPH(parseToDouble(edtPH.getText().toString()));
        else
            waterReport.setWatPH(0);

        waterReport.setWatCea(getCeaInDs_m());

        return waterReport;
    }

    /**
     * convert a String numeric value to double
     * @param valueInText value in String
     * @return double equivalent
     */
    private double parseToDouble(String valueInText){

        try {
            return Double.parseDouble(valueInText);
        }catch (Exception e){
            return 0;
        }

    }

    private double getCeaInDs_m() {
        Double cea;
        if(edtCea.getText().length() > 0)
            cea = Double.parseDouble(edtCea.getText().toString());
        else
            cea = 0.0;

        if(cea != 0){
            //Caso estiver no formato uS/cm, é necessário dividir por 1000 para transformar em dS/m.
            //dS/m e mS/cm são valores equivalentes
            if(spinnerCEa.getSelectedItemPosition() == 2)
                cea = cea / 1000;

        }

        return cea;
    }

    private void initiate() {
        edtCea = findViewById(R.id.EDIT_CEA);
        edtCa = findViewById(R.id.EDIT_CA);
        edtMg = findViewById(R.id.EDIT_MG);
        edtK = findViewById(R.id.EDIT_K);
        edtNa = findViewById(R.id.EDIT_NA);
        edtCo3 = findViewById(R.id.EDIT_CO3);
        edtHco3 = findViewById(R.id.EDIT_HCO3);
        edtCl = findViewById(R.id.EDIT_CL);
        edtPH = findViewById(R.id.EDIT_PH);

        analyzeButton = findViewById(R.id.ANALYZE_BUTTON);

        spinnerMolecules = findViewById(R.id.MOLECULES_SPINNER);
        spinnerMolecules.setOnItemSelectedListener(this);

        spinnerCEa = findViewById(R.id.CEA_SPINNER);
        spinnerCEa.setOnItemSelectedListener(this);

    }

    /**
     * é obrigatório sobscrever o método...
     * manipula escolha nos spinners
     * @param parent parent
     * @param arg1 argumento
     * @param position posição
     * @param id id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position, long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.MOLECULES_SPINNER)
        {
            switch (position) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
        }
        else if(spinner.getId() == R.id.CEA_SPINNER)
        {
            switch (position) {
                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
