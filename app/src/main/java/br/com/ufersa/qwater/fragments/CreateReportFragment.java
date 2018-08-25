package br.com.ufersa.qwater.fragments;

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
import android.widget.Toast;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.activities.AnalyzeReportActivity;
import br.com.ufersa.qwater.beans.Report;

import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE_REPORT;

public class CreateReportFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText edtCea, edtCa, edtMg, edtK, edtNa, edtCo3, edtHco3, edtCl, edtPH, edtSO4, edtB;
    private Button analyzeButton;
    private Spinner spinnerCEa;
    private boolean isUpdatingReport = false;
    private Report report;

    //TODO na CaTable, o valor limite para a Cea é 8.0
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_report, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.analisar_relatorio));

        initiate(view);

        // checa se o bundle vem vazio, se contem algo, é para dar update
        // Se o fragmento está atualizando o relatório, não é necessário instanciar um novo relatório
        // Dessa forma, o ID e o nome da fonte não são perdidos
        if (this.getArguments() != null) {
            try {
                report = this.getArguments().getParcelable(REPORT);
                updateUI(report);
                isUpdatingReport = true;
            }catch (Exception e) {
                e.printStackTrace();
                report = new Report();
                Toast.makeText(view.getContext(), R.string.erro_atualizar_relatorio, Toast.LENGTH_LONG).show();
            }
        }else
            report = new Report();

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    report = generateReport();
                    // Se nenhum parametro for NaN, procede.
                    if (!inputIsInvalid(report)) {

                        Intent intent = new Intent(view.getContext(), AnalyzeReportActivity.class);
                        intent.putExtra(REPORT, report);

                        // Sinaliza que o relatório está vindo para ser atualizado (ao invés de insert, será update no BD)
                        if(isUpdatingReport)
                            intent.putExtra(GOING_TO, UPDATE_REPORT);

                        startActivity(intent);

                    } else
                        Toast.makeText(getContext(), getString(R.string.erro_verifique_dados), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), getString(R.string.erro_verifique_dados), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updateUI(Report report){
        // troca o texto do botão quando vem para update
        analyzeButton.setText(R.string.avaliar_antes_de_atualizar);

        edtCea.setText(String.valueOf(report.getCea()));
        edtCa.setText(String.valueOf(report.getCa()));
        edtMg.setText(String.valueOf(report.getMg()));
        edtK.setText(String.valueOf(report.getK()));
        edtNa.setText(String.valueOf(report.getNa()));
        edtCo3.setText(String.valueOf(report.getCo3()));
        edtHco3.setText(String.valueOf(report.getHco3()));
        edtCl.setText(String.valueOf(report.getCl()));
        edtPH.setText(String.valueOf(report.getPh()));
        edtB.setText(String.valueOf(report.getB()));
        edtSO4.setText(String.valueOf(report.getSo4()));

    }

    /**
     * Verifica se algum dos parametros vindos dos textviews é NaN
     * @param report relatório
     * @return true caso algum parametro seja NaN
     */
    private boolean inputIsInvalid(Report report) {
        try {
            return  (Double.isNaN(report.getCea()) || Double.isNaN(report.getCa())
                    || Double.isNaN(report.getMg()) || Double.isNaN(report.getK())
                    || Double.isNaN(report.getNa()) || Double.isNaN(report.getCo3())
                    || Double.isNaN(report.getCl()) || Double.isNaN(report.getHco3())
                    || Double.isNaN(report.getPh()) || Double.isNaN(report.getSo4())
                    || Double.isNaN(report.getB()));
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private Report generateReport() {

        if(edtMg.getText().length() > 0)
            report.setMg(parseToDouble(edtMg.getText().toString()));
        else
            report.setMg(0.0);

        if(edtK.getText().length() > 0)
            report.setK(parseToDouble(edtK.getText().toString()));
        else
            report.setK(0.0);

        if(edtCa.getText().length() > 0)
            report.setCa(parseToDouble(edtCa.getText().toString()));
        else
            report.setCa(0.0);

        if(edtNa.getText().length() > 0)
            report.setNa(parseToDouble(edtNa.getText().toString()));
        else
            report.setNa(0.0);

        if(edtCl.getText().length() > 0)
            report.setCl(parseToDouble(edtCl.getText().toString()));
        else
            report.setCl(0.0);

        if(edtCo3.getText().length() > 0)
            report.setCo3(parseToDouble(edtCo3.getText().toString()));
        else
            report.setCo3(0.0);

        if(edtHco3.getText().length() > 0)
            report.setHco3(parseToDouble(edtHco3.getText().toString()));
        else
            report.setHco3(0.0);

        if(edtPH.getText().length() > 0)
            report.setPh(parseToDouble(edtPH.getText().toString()));
        else
            report.setPh(0.0);

        if(edtSO4.getText().length() > 0)
            report.setSo4(parseToDouble(edtSO4.getText().toString()));
        else
            report.setSo4(0.0);

        if(edtB.getText().length() > 0)
            report.setB(parseToDouble(edtB.getText().toString()));
        else
            report.setB(0.0);

        report.setCea(getCeaInDs_m());

        return report;
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
        edtB = view.findViewById(R.id.EDIT_B);
        edtSO4 = view.findViewById(R.id.EDIT_SO4);

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
