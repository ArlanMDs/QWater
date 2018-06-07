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

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.WaterReport;

public class Tab1 extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText edtCea, edtCa, edtMg, edtK, edtNa, edtCo3, edtHco3, edtCl, edtPH;
    private interfaceDataCommunicator mCallback;
    private Button analyzeButton;
    private Spinner spinnerCEa, spinnerMolecules;
    private Double ca, mg, na, cea, hco3, k=0.0, co3=0.0, cl=0.0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab1, container, false);
    }
  
    /**
     * Interface de comunicação do RAS
     */
    public interface interfaceDataCommunicator {
        void sendData(WaterReport waterReport);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initiate();

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            double cea = 0.0, correctedSAR = 0.0, normalSAR = 0.0;
//            /*
//            checa ser o length é maior que zero para garantir que tem algum valor digitado
//             */
//            if (edtCea.getText().length() > 0) {
//                cea = getCeaInDs_m();
//            }
//
//            if (edtCa.getText().length() > 0 && edtMg.getText().length() > 0 && edtNa.getText().length() > 0 && edtCea.getText().length() > 0 && edtHco3.getText().length() > 0) {
//                correctedSAR = calculateCorrectedSAR();
//            }
//
//            if (edtCa.getText().length() > 0 && edtMg.getText().length() > 0 && edtNa.getText().length() >0){
//                normalSAR = calculateNormalSAR();
//            }
//
//            //TODO mudar para que seja possível fazer os cálculos separadamente
//            if (cea != 0.0 && correctedSAR != 0.0 && normalSAR != 0.0) {
//                //se os dados estiverem ok, é gerado um novo relatório e enviado para a outra tab
//                WaterReport waterReport = generateReport(normalSAR, correctedSAR, cea);
//                mCallback.sendData(waterReport);
//
//                ViewPager viewPager = getActivity().findViewById(R.id.container);
//                viewPager.setCurrentItem(1);
//
//            }else
//                Toast.makeText(getContext(), getString(R.string.erroNaLeituraDeDados) , Toast.LENGTH_LONG).show();

                WaterReport waterReport = generateReport();
                mCallback.sendData(waterReport);

                ViewPager viewPager = getActivity().findViewById(R.id.container);
                viewPager.setCurrentItem(1);

            }
        });


    }

    /*
        uma ideia é que ao clicar em analisar, todos os campos sejam adicionado a um objeto waterReport, esse objeto será passado para outra activity e fazer somente lá os cálculos que forem possíveis
        caso não haja valores null ou zero. Daria mais certo zero...
     */
    private WaterReport generateReport(/*double normalSAR, double correctedSAR, double CEa*/) {
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
        //waterReport.setWatNormalSar(normalSAR);
        //waterReport.setWatCorrectedSar(correctedSAR);

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


    /**
     * O calculo da salinidade é feita com base apenas no valor do cea. O valor do TextView é passado para o Fragmento de exibição Tab2
     */
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
        View view = getView();
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

        spinnerMolecules = view.findViewById(R.id.MOLECULES_SPINNER);
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
    public void onItemSelected(AdapterView<?> parent, View arg1, int position,long id) {

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
