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
import br.com.ufersa.qwater.models.RAS;

public class Tab1 extends Fragment implements AdapterView.OnItemSelectedListener{

    private EditText edt_CEa, edt_Ca, edt_Mg, edt_K, edt_Na, edt_CO3, edt_HCO3, edt_Cl;
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
        void sendData(double correctedRas, double sal);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double sal=0.0, correctedRas=0.0;
                if (edt_CEa.getText().length() > 0) {
                    sal = calculaSalinidade();
                }
                if (edt_Ca.getText().length() > 0 && edt_Mg.getText().length() > 0 && edt_Na.getText().length() > 0 && edt_CEa.getText().length() > 0 && edt_HCO3.getText().length() > 0) {
                    correctedRas = calculaRASCorrigido();
                }
                if (sal != 0.0 && correctedRas != 0.0) {
                    mCallback.sendData(correctedRas, sal);
                    ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.container);
                    viewPager.setCurrentItem(1);
                }else
                    Toast.makeText(getContext(), "Ocorreu algum erro na leitura dos dados" , Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Usa uma instância da classe RAS para calcular o valor do ras corrigido
     */
    private double calculaRASCorrigido() {
        RAS ras;
        Double Ca, Mg, Na, CEa, HCO3;
        try {
            Ca = Double.parseDouble(this.edt_Ca.getText().toString());
            Mg = Double.parseDouble(this.edt_Mg.getText().toString());
            Na = Double.parseDouble(this.edt_Na.getText().toString());
            CEa = Double.parseDouble(this.edt_CEa.getText().toString());
            HCO3 =  Double.parseDouble(this.edt_HCO3.getText().toString());
            ras = new RAS(Ca, Mg, Na, CEa, HCO3);
        }catch(Exception e){
            Toast.makeText(getContext(), "Algum valor digitado está com formato incorreto", Toast.LENGTH_SHORT).show();
            ras = null;
        }
        if(ras!= null)
            return ras.calculaRASCorrigido(spinnerMolecules.getSelectedItemPosition(), spinnerCEa.getSelectedItemPosition());

        return 0.0;
    }

    /**
     * O calculo da salinidade é feita com base apenas no valor do CEa. O valor do TextView é passado para o Fragmento de exibição Tab2
     */
    private double calculaSalinidade() {
        Double result;
        try {
            result = Double.parseDouble(this.edt_CEa.getText().toString());
        }catch (Exception e){
            result=null;
            Toast.makeText(getContext(), "O valor do CEa digitado está com formato incorreto", Toast.LENGTH_SHORT).show();
        }
        if(result != null)
            return result;

        return 0.0;
    }

    private void findViews() {
        view = getView();
        edt_CEa = (EditText)view.findViewById(R.id.CEa);
        edt_Ca = (EditText)view.findViewById(R.id.Ca);
        edt_Mg = (EditText)view.findViewById(R.id.Mg);
        edt_K = (EditText)view.findViewById(R.id.K);
        edt_Na = (EditText)view.findViewById(R.id.Na);
        edt_CO3 = (EditText)view.findViewById(R.id.CO3);
        edt_HCO3 = (EditText)view.findViewById(R.id.HCO3);
        edt_Cl = (EditText)view.findViewById(R.id.Cl);
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
