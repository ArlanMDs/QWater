package br.com.ufersa.qwater.fragments;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.ufersa.qwater.R;

/**
 * Created by Arlan on 28-Aug-17.
 */

public class Tab2 extends Fragment implements View.OnClickListener {

    private Button detalhesRAS, detalhesSalinidade;
    private TextView resultadoRAS, resultadoSalinidade;
    private String classificacaoRAS, classificacaoSalinidade;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab2, container, false);
        
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews();


    }


    /**
     * método que faz a comunicação da RAS entre a tab1 e tab2
     * @param correctedRas valor recebido da tab1
     */
    public void updateData(double correctedRas, double sal){
        mostraClassificacaoRAS(correctedRas);
        mostraClassificacaoSalinidade(sal);

    }

    /**
     * mostra uma janela contendo informações em texto sobre a classificação do RAS ou salinidade
     * @param string RAS ou salinidade a ser classificada
     */
    public void showInfoDialogue(String string){

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        int resourceId = getContext().getResources().
                getIdentifier(string, "string", getContext().getPackageName());
        builder.setTitle("Classificação "+ string +":")
                .setMessage(getString(resourceId))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //fecha o alerta
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * classifica a salinidade de acordo com os intervalos dados
     * @param CEa parametro de classificação
     */
    private void mostraClassificacaoSalinidade(double CEa) {
        if(CEa < 0.75)
            classificacaoSalinidade = "C1";
        else if(CEa >= 0.75 && CEa < 1.50)
            classificacaoSalinidade = "C2";
        else if(CEa >=1.50 && CEa < 3.0)
            classificacaoSalinidade = "C3";
        else
            classificacaoSalinidade = "C4";

        resultadoSalinidade.setVisibility(View.VISIBLE);
        detalhesSalinidade.setVisibility(View.VISIBLE);
        resultadoSalinidade.setText("Risco de Salinidade com CEa: "+ String.valueOf(CEa)+ "\nClassificação: "+ classificacaoSalinidade);
    }

    /**
     * classifica o valor do RAS entre intervalos
     * @param resultadoRAS valor do RAS
     */
    private void mostraClassificacaoRAS(Double resultadoRAS) {

        if(resultadoRAS < 10.0)
            classificacaoRAS = "S1";
        else if(resultadoRAS >= 10.0 && resultadoRAS < 18.0)
            classificacaoRAS = "S2";
        else if(resultadoRAS >=18.0 && resultadoRAS < 26.0)
            classificacaoRAS = "S3";
        else
            classificacaoRAS = "S4";

        this.resultadoRAS.setVisibility(View.VISIBLE);
        detalhesRAS.setVisibility(View.VISIBLE);
        this.resultadoRAS.setText("RAS: "+String.valueOf(resultadoRAS)+ "\nClassificação: "+ classificacaoRAS);

    }

    private void findViews() {
        resultadoRAS = (TextView)getView().findViewById(R.id.resultadoRAS);
        detalhesRAS = (Button)getView().findViewById(R.id.detalhesRAS);
        resultadoSalinidade = (TextView)getView().findViewById(R.id.resultadoSalinidade);
        detalhesSalinidade = (Button)getView().findViewById(R.id.detalhesSalinidade);

    }

    /**
     * manipula os clicks nos botões
     * @param v id do botão
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.detalhesRAS:
                showInfoDialogue(classificacaoRAS);

                break;

            case R.id.detalhesSalinidade:
                showInfoDialogue(classificacaoSalinidade);

                break;
        }
    }
}
