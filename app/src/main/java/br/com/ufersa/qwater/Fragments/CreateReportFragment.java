package br.com.ufersa.qwater.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.AnalyzeWaterReportActivity;
import br.com.ufersa.qwater.beans.WaterReport;

public class CreateReportFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText edtCea, edtCa, edtMg, edtK, edtNa, edtCo3, edtHco3, edtCl, edtPH;
    private Button analyzeButton;
    private Spinner spinnerCEa;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.fragment_create_report, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Criar relatório");

        initiate(view);

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AnalyzeWaterReportActivity.class);
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

    private void initiate(View view) {
        edtCea = view.findViewById(R.id.EDIT_CEA);
        edtCa = view.findViewById(R.id.EDIT_CA);
        edtMg = view.findViewById(R.id.EDIT_MG);
        edtK = view.findViewById(R.id.EDIT_K);
        edtNa = view.findViewById(R.id.EDIT_NA);
        edtCo3 = view.findViewById(R.id.EDIT_CO3);
        edtHco3 = view.findViewById(R.id.EDIT_HCO3);
        edtCl = view.findViewById(R.id.EDIT_CL);
        edtPH = view.findViewById(R.id.EDIT_PH);

        analyzeButton = view.findViewById(R.id.ANALYZE_BUTTON);

        Spinner spinnerMolecules = view.findViewById(R.id.MOLECULES_SPINNER);
        spinnerMolecules.setOnItemSelectedListener(this);

        spinnerCEa = view.findViewById(R.id.CEA_SPINNER);
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
