package br.com.ufersa.qwater.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.models.SARCalculator;
import br.com.ufersa.qwater.models.Report;

public class Tab1 extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText edtCea, edtCa, edtMg, edtK, edtNa, edtCo3, edtHco3, edtCl;
    private interfaceDataCommunicator mCallback;
    private Button calcular;
    private View view;
    private Spinner spinnerCEa, spinnerMolecules;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);
    }

    /**
     * Interface de comunicação do RAS
     */
    public interface interfaceDataCommunicator {
        void sendData(Report report);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViewsIDs();

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            double CEa = 0.0, correctedSAR = 0.0, normalSAR = 0.0;
            /*
            checa ser o length é maior que zero para garantir que tem algum valor digitado
             */
            if (edtCea.getText().length() > 0) {
                CEa = calculateSalinity();
            }

            if (edtCa.getText().length() > 0 && edtMg.getText().length() > 0 && edtNa.getText().length() > 0 && edtCea.getText().length() > 0 && edtHco3.getText().length() > 0) {
                correctedSAR = calculateCorrectedSAR();
            }

            if (edtCa.getText().length() > 0 && edtMg.getText().length() > 0 && edtNa.getText().length() >0){
                normalSAR = calculateNormalSAR();
            }

            //TODO mudar para que seja possível fazer os cálculos separadamente
            if (CEa != 0.0 && correctedSAR != 0.0 && normalSAR != 0.0) {

                //se os dados estiverem ok, é gerado um novo relatório e enviado para a outra tab
                Report report = generateReport(normalSAR, correctedSAR, CEa);
                mCallback.sendData(report);

                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.container);
                viewPager.setCurrentItem(1);

            }else
                Toast.makeText(getContext(), getString(R.string.erroNaLeituraDeDados) , Toast.LENGTH_LONG).show();
            }
        });

    }

    private Report generateReport(double normalSAR, double correctedSAR, double CEa) {
        Report report = new Report();

        report.setNormalSAR(normalSAR);
        report.setCorrectedSAR(correctedSAR);
        report.setCea(CEa);
        return report;
    }

    private double calculateNormalSAR(){
        SARCalculator sarCalculator;
        Double Ca, Mg, Na;
        try {
            Ca = Double.parseDouble(this.edtCa.getText().toString());
            Mg = Double.parseDouble(this.edtMg.getText().toString());
            Na = Double.parseDouble(this.edtNa.getText().toString());
            sarCalculator = new SARCalculator(Ca, Mg, Na);
        }catch(Exception e){
            Toast.makeText(getContext(), getString(R.string.valorIncorreto), Toast.LENGTH_LONG).show();
            sarCalculator = null;
        }
        if(sarCalculator != null)
            return sarCalculator.calculateSAR(spinnerMolecules.getSelectedItemPosition());

        return 0.0;
    }

    /**
     * Usa uma instância da classe RAS para calcular o valor do ras corrigido
     */
    private double calculateCorrectedSAR() {
        SARCalculator sarCalculator;
        Double Ca, Mg, Na, CEa, HCO3;
        try {
            Ca = Double.parseDouble(this.edtCa.getText().toString());
            Mg = Double.parseDouble(this.edtMg.getText().toString());
            Na = Double.parseDouble(this.edtNa.getText().toString());
            CEa = Double.parseDouble(this.edtCea.getText().toString());
            HCO3 =  Double.parseDouble(this.edtHco3.getText().toString());
            sarCalculator = new SARCalculator(Ca, Mg, Na, CEa, HCO3);
        }catch(Exception e){
            Toast.makeText(getContext(), getString(R.string.valorIncorreto), Toast.LENGTH_LONG).show();
            sarCalculator = null;
        }
        if(sarCalculator != null)
            return sarCalculator.calculateCorrectedSAR(spinnerMolecules.getSelectedItemPosition(), spinnerCEa.getSelectedItemPosition());

        return 0.0;
    }

    /**
     * O calculo da salinidade é feita com base apenas no valor do CEa. O valor do TextView é passado para o Fragmento de exibição Tab2
     */
    private double calculateSalinity() {
        Double result;
        try {
            result = Double.parseDouble(this.edtCea.getText().toString());
        }catch (Exception e){
            result = null;
            Toast.makeText(getContext(), getString(R.string.valorIncorreto), Toast.LENGTH_LONG).show();
        }
        if(result != null)
            return result;
        //TODO esse result precisa estar formatado em dS/m
        return 0.0;
    }

    private void findViewsIDs() {
        view = getView();
        edtCea = (EditText)view.findViewById(R.id.cea);
        edtCa = (EditText)view.findViewById(R.id.Ca);
        edtMg = (EditText)view.findViewById(R.id.Mg);
        edtK = (EditText)view.findViewById(R.id.K);
        edtNa = (EditText)view.findViewById(R.id.Na);
        edtCo3 = (EditText)view.findViewById(R.id.co3);
        edtHco3 = (EditText)view.findViewById(R.id.hco3);
        edtCl = (EditText)view.findViewById(R.id.Cl);
        calcular = (Button)view.findViewById(R.id.calcular);

        spinnerMolecules = (Spinner) view.findViewById(R.id.spinnerMolecules);
        spinnerMolecules.setOnItemSelectedListener(this);

        spinnerCEa = (Spinner) view.findViewById(R.id.spinnerCEa);
        spinnerCEa.setOnItemSelectedListener(this);

    }

    /**
     * manipula escolha nos spinners
     * @param parent parent
     * @param arg1 argumento
     * @param position posição
     * @param id id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {

        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.spinnerMolecules)
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
        else if(spinner.getId() == R.id.spinnerCEa)
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

    /**
     * Versões antigas do android usaram a Activity, enquanto as mais novas usam o Context como parâmetro
     * @param context activity or context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity){
            activity = (Activity) context;
            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (interfaceDataCommunicator) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement interface");
            }
        }else{
            try {
                mCallback = (interfaceDataCommunicator) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + " must implement interface");
            }
        }
    }

    @Override
    public void onDetach() {
        mCallback = null; //avoid leaking
        super.onDetach();
    }
}
