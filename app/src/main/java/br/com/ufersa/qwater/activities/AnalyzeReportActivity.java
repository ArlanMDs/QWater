package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Report;
import br.com.ufersa.qwater.database.AppDatabase;
import br.com.ufersa.qwater.info_activities.CoherenceActivity;
import br.com.ufersa.qwater.info_activities.PhActivity;
import br.com.ufersa.qwater.info_activities.SalinityActivity;
import br.com.ufersa.qwater.info_activities.SarActivity;
import br.com.ufersa.qwater.info_activities.ToxityActivity;
import br.com.ufersa.qwater.util.SARCalculator;

import static br.com.ufersa.qwater.util.Flags.ALERT;
import static br.com.ufersa.qwater.util.Flags.ANALYZE_REPORT_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.CALLING_ACTIVITY;
import static br.com.ufersa.qwater.util.Flags.CAUTION;
import static br.com.ufersa.qwater.util.Flags.DANGER;
import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.INSERT_SELECT_DATE;
import static br.com.ufersa.qwater.util.Flags.INSERT_SELECT_SOURCE;
import static br.com.ufersa.qwater.util.Flags.OK;
import static br.com.ufersa.qwater.util.Flags.REPORT;
import static br.com.ufersa.qwater.util.Flags.SEE_REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE_REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE_SELECT_DATE;
import static br.com.ufersa.qwater.util.Flags.UPDATE_SELECT_SOURCE;

public class AnalyzeReportActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView salinityTextview, SARClassificationTextview, clClassificationTextview, bClassificationTextview, phClassificationTextview, coherenceClassificationTextview, correctedSARTextview;
    private TextView na1ClassificationTextview, na2ClassificationTextview;// o 1 se refere à irrigação por superfície, o 2 à irrigação por aspersão
    private ImageView coherenceStatus, salynityStatus, sarStatus, toxityStatus, phStatus;
    private Report report;
    private AppDatabase appDatabase;
    private int currentToxityValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze_report);

        initiate();
        getIncomingIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Muda o menu caso o usuário esteja atualizando um report, vendo o relatório ou inserindo um novo
        if(getIntent().hasExtra(REPORT)) {
            if (getIntent().getIntExtra(GOING_TO, 0) == UPDATE_REPORT)
                getMenuInflater().inflate(R.menu.analyze_update, menu);
            else if (getIntent().getIntExtra(GOING_TO, 0) == SEE_REPORT)
                getMenuInflater().inflate(R.menu.analyze_see, menu);
            else
                getMenuInflater().inflate(R.menu.analyze_insert, menu);
        }//TODO rever isso aqui...

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_insert:
                // abre uma nova activity e passa o relatório, lá, será inserida a data da amostra e o nome da fonte
                startActivityForResult(new Intent(AnalyzeReportActivity.this, SelectDateActivity.class), INSERT_SELECT_DATE);

            return true;

            case R.id.action_update:

                try{
                    // Pronto para atualizar no BD
                    new AsyncUpdate().execute();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(AnalyzeReportActivity.this, getString(R.string.erro_atualizar_relatorio), Toast.LENGTH_SHORT).show();
                }

            return true;

            case R.id.action_update_source:
                Intent intent = new Intent(AnalyzeReportActivity.this, ListSourcesActivity.class);
                intent.putExtra(CALLING_ACTIVITY, ANALYZE_REPORT_ACTIVITY);
                startActivityForResult(intent, UPDATE_SELECT_SOURCE);

            return true;

            case R.id.action_update_date:
                startActivityForResult(new Intent(AnalyzeReportActivity.this, SelectDateActivity.class), UPDATE_SELECT_DATE);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateActivityReport(Report report){
        // TODO as coisas não estão no lugar certo...
        try {
            report.setNormalSar(calculateNormalSAR(report.getCa(), report.getMg(), report.getNa()));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(AnalyzeReportActivity.this, R.string.erro_calcular_ras, Toast.LENGTH_SHORT).show();
            report.setNormalSar(0.0);
        }
        try {
            report.setCorrectedSar(calculateCorrectedSAR(report.getCa(), report.getMg(), report.getNa(), report.getHco3(), report.getCea()));
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(AnalyzeReportActivity.this, R.string.erro_calcular_ras_corrigida, Toast.LENGTH_SHORT).show();
            report.setCorrectedSar(0.0);
        }

        // A classificação e armazenamento está sendo feito após o arredondamento das casas decimais!
        this.report = report;

        updateUI();

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(REPORT)) {
            updateActivityReport((Report) getIntent().getParcelableExtra(REPORT));
        }
    }

    /**
     * Calcula a RAS normal
     * @param ca cálcio
     * @param mg magnésio
     * @param na sódio
     * @return valor da RAS normal ou zero em caso de erro
     */
    private double calculateNormalSAR(double ca, double mg, double na){

        if(ca != 0.0 && mg != 0.0 && na != 0.0) { // Calcula a RAS e arredonda o resultado
            SARCalculator sarCalculator = new SARCalculator();
            return formatFractionDigits(sarCalculator.calculateNormalSAR(1, ca, mg, na));//TODO SPINNNNER
        }else
            return Double.NaN;

    }

    /**
     * Calcula a RAS corrigida
     * @param ca cálcio
     * @param mg magnésio
     * @param na sódio
     * @param hco3 bicarbonato
     * @param cea condutividade elétrica
     * @return valor da RAS corrigida, zero em caso de erro.
     */
    private double calculateCorrectedSAR(double ca, double mg, double na, double hco3, double cea) {

        if(ca != 0.0 & mg != 0.0 & na != 0.0 & hco3 != 0.0 & cea != 0.0) {
            SARCalculator sarCalculator = new SARCalculator();
            return formatFractionDigits(sarCalculator.calculateCorrectedSAR(1, 1, ca, mg, na, cea, hco3));//TODO implementar as conversões dos spinners
        }else
            return Double.NaN;
    }

    private void updateUI(){
        //primeiro gera a string que representa a classificação, depois cria o ID a partir dessa string e busca seu valor no strings.xml

        // textview com valor da RAS*
        if(!Double.isNaN(report.getCorrectedSar()))
            correctedSARTextview.setText(String.valueOf(report.getCorrectedSar()));
        else {
            correctedSARTextview.setText(getString(R.string.ras_nao_calculado));
        }
        // riscos de infiltração
        if(!Double.isNaN(report.getCorrectedSar()))
            SARClassificationTextview.setText(getStringIDFromResources(getSARClassificationID(report.getCorrectedSar())));
        else
            SARClassificationTextview.setText(getString(R.string.ras_nao_informado));

        // salinidade
        if (report.getCea() != 0)
            salinityTextview.setText(getStringIDFromResources(getSalinityClassificationID(report.getCea())));
        else
            salinityTextview.setText(getString(R.string.cea_nao_informado));

        // Toxidez de íons específicos

        // Cloreto
        if(report.getCl() != 0)
            clClassificationTextview.setText(getStringIDFromResources(getClClassificationID(report.getCl())));
        else
            clClassificationTextview.setText(getString(R.string.cl_nao_informado));

        // boro
        if(report.getB() != 0)
            bClassificationTextview.setText(getStringIDFromResources(getBClassificationID(report.getB())));
        else
            bClassificationTextview.setText(getString(R.string.boro_nao_informado));

        /* Sódio
            SS = Irrigação por superfície
            SA = Irrigação por aspersão
        */

        // na1
        if(!Double.isNaN(report.getCorrectedSar()))
            na1ClassificationTextview.setText(getStringIDFromResources(getNa1ClassificationID(report.getCorrectedSar())));
        else
            na1ClassificationTextview.setText(getString(R.string.na1_nao_informado));

        // na2
        if(report.getNa() != 0)
            na2ClassificationTextview.setText(getStringIDFromResources(getNa2ClassificationID(report.getNa())));
        else
            na2ClassificationTextview.setText(getString(R.string.na2_nao_informado));

        // pH
        if(report.getPh() != 0)
            phClassificationTextview.setText(getStringIDFromResources(getPhClassificationID(report.getPh())));
        else
            phClassificationTextview.setText(getString(R.string.ph_nao_informado));

        // Coherence
        if(userTypedAllCationsAndAnions())
            coherenceClassificationTextview.setText(getStringIDFromResources(getCoherenceClassificationID()));
        else
            coherenceClassificationTextview.setText(getString(R.string.coerencia_nao_informado));

    }

    private boolean userTypedAllCationsAndAnions() {

        return report.getMg() != 0 && report.getK() != 0 && report.getCa() != 0 && report.getNa() != 0 && report.getCl() != 0 && report.getCo3() != 0 && report.getSo4() != 0 && report.getHco3() != 0;
    }

    private int getStringIDFromResources(String classification) {
        return this.getResources().getIdentifier(classification, "string", this.getPackageName());
    }

    private String getSalinityClassificationID(double CEa) {

        if(CEa < 0.75) {
            updateStatus(salynityStatus, OK);
            return "C1";
        }
        else if(CEa >= 0.75 && CEa < 1.50) {
            updateStatus(salynityStatus, CAUTION);
            return "C2";
        }
        else if(CEa >=1.50 && CEa <= 3.0) {
            updateStatus(salynityStatus, ALERT);
            return "C3";
        }
        else {
            updateStatus(salynityStatus, DANGER);
            return "C4";
        }
    }

    private String getSARClassificationID(double correctedSAR) {

        if (correctedSAR < 10.0) {
            updateStatus(sarStatus, OK);
            return "S1";
        }
        else if (correctedSAR >= 10.0 && correctedSAR < 18.0) {
            updateStatus(sarStatus, CAUTION);
            return "S2";
        }
        else if (correctedSAR >= 18.0 && correctedSAR < 26.0) {
            updateStatus(sarStatus, ALERT);
            return "S3";
        }
        else {
            updateStatus(sarStatus, DANGER);
            return "S4";
        }
    }

    private String getClClassificationID(double cl) {

        if(cl <= 2.0) {
            updateStatus(toxityStatus, OK);
            return "CL1";
        }
        else if(cl > 2.0 && cl <= 4.0) {
            updateStatus(toxityStatus, CAUTION);
            return "CL2";
        }
        else if(cl >4.0 && cl <= 10.0) {
            updateStatus(toxityStatus, ALERT);
            return "CL3";
        }
        else {
            updateStatus(toxityStatus, DANGER);
            return "CL4";
        }
    }

    /* Sódio
        SS = Irrigação por superfície
        SA = Irrigação por aspersão
    */
    private String getNa1ClassificationID(double correctedSAR) {

        if (correctedSAR < 3.0) {
            updateStatus(toxityStatus, OK);
            return "SS1";
        }
        else if (correctedSAR >= 3.0 && correctedSAR <= 9.0) {
            updateStatus(toxityStatus, CAUTION);
            return "SS2";
        }
        else {
            updateStatus(toxityStatus, DANGER);
            return "SS3";
        }
    }

    private String getNa2ClassificationID(double na) {

        if (na <= 3.0) {
            updateStatus(toxityStatus, OK);
            return "SA1";
        }
        else {
            updateStatus(toxityStatus, CAUTION);
            return "SA2";
        }
    }

    private String getBClassificationID(double b) {

        if(b <= 0.5) {
            updateStatus(toxityStatus, OK);
            return "B1";
        }
        else if(b > 0.5 && b <= 1.0) {
            updateStatus(toxityStatus, CAUTION);
            return "B2";
        }
        else if(b >1.0 && b <= 2.0) {
            updateStatus(toxityStatus, ALERT);
            return "B3";
        }
        else {
            updateStatus(toxityStatus, DANGER);
            return "B4";
        }
    }

    private String getPhClassificationID(double pH) {

        if (pH < 7.0) {
            updateStatus(phStatus, OK);
            return "PH1";
        }
        else if (pH >= 7.0 && pH <= 8.0) {
            updateStatus(phStatus, CAUTION);
            return "PH2";
        }
        else {
            updateStatus(phStatus, DANGER);
            return "PH3";
        }
    }

    private String getCoherenceClassificationID() {

        double cations = report.getMg() + report.getK() + report.getCa() + report.getNa();
        double anions = report.getCl() + report.getCo3() + report.getSo4() + report.getHco3();
        double r = Math.abs((cations - anions) / (cations + anions))*100;

        if (r < 5.0) {
            updateStatus(coherenceStatus, OK);
            return "CO1";
        }
        else if (r >= 5.0 && r <= 10.0) {
            updateStatus(coherenceStatus, CAUTION);
            return "CO2";
        }
        else {
            updateStatus(coherenceStatus, DANGER);
            return "CO3";
        }
    }

    private void updateStatus(ImageView imageView, int status) {
        // Caso a view seja a de toxidez, a maneira de fazer o trabalho é um pouco diferente, pois são vários elementos que podem mudar uma única bolinha.
        // E apenas o status mais alto pode ser considerado.
        // Para evitar criar um método quase igual, vai ser comparada a view recebida...
        // O código vai ficar um pouco repetitivo, mas é melhor do que criar outro método praticamente igual.
        if (imageView.getId() == R.id.TOXITY_STATUS ){
            if(status > currentToxityValue) {
                currentToxityValue = status;

                switch (status) {
                    case OK:
                        imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_ok));
                        break;
                    case CAUTION:
                        imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_caution));
                        break;
                    case ALERT:
                        imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_alert));
                        break;
                    case DANGER:
                        imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_danger));
                        break;
                }
            }
        } else {
            switch (status) {
                case OK:
                    imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_ok));
                    break;
                case CAUTION:
                    imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_caution));
                    break;
                case ALERT:
                    imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_alert));
                    break;
                case DANGER:
                    imageView.setImageDrawable(ContextCompat.getDrawable(AnalyzeReportActivity.this, R.drawable.status_danger));
                    break;
            }
        }
    }

    /**
     * Arredenda o valor de um double para 4 casas decimais
     * @param value valor a ser arredondado
     * @return valor arredondado
     */
    private double formatFractionDigits(double value){
        try {
            return (Math.ceil(value *10000.0 )) / 10000.0;
        }catch (Exception e){
            e.printStackTrace();
            return value;
        }
    }

    private void initiate() {
        SARClassificationTextview = findViewById(R.id.SAR_CLASSIFICATION_TEXTVIEW);
        salinityTextview = findViewById(R.id.SALINITY_TEXTVIEW);
        clClassificationTextview = findViewById(R.id.CL_CLASSIFICATION_TEXTVIEW);
        bClassificationTextview = findViewById(R.id.B_CLASSIFICATION_TEXTVIEW);
        na1ClassificationTextview = findViewById(R.id.NA1_CLASSIFICATION_TEXTVIEW);
        na2ClassificationTextview = findViewById(R.id.NA2_CLASSIFICATION_TEXTVIEW);
        phClassificationTextview = findViewById(R.id.PH_CLASSIFICATION_TEXTVIEW);
        coherenceClassificationTextview = findViewById(R.id.COHERENCE_TEXTVIEW);
        correctedSARTextview = findViewById(R.id.CORRECTED_SAR_TEXTVIEW);
        // status
        coherenceStatus = findViewById(R.id.COHERENCE_STATUS);
        salynityStatus = findViewById(R.id.SALINITY_STATUS);
        sarStatus = findViewById(R.id.SAR_STATUS);
        toxityStatus = findViewById(R.id.TOXITY_STATUS);
        phStatus = findViewById(R.id.PH_STATUS);
        // info
        ImageButton coherenceInfo = findViewById(R.id.COHERENCE_INFO);
        coherenceInfo.setOnClickListener(this);
        ImageButton salynityInfo = findViewById(R.id.SALINITY_INFO);
        salynityInfo.setOnClickListener(this);
        ImageButton sarInfo = findViewById(R.id.SAR_INFO);
        sarInfo.setOnClickListener(this);
        ImageButton toxityInfo = findViewById(R.id.TOXITY_INFO);
        toxityInfo.setOnClickListener(this);
        ImageButton phInfo = findViewById(R.id.PH_INFO);
        phInfo.setOnClickListener(this);

        // prepara o bd
        appDatabase = AppDatabase.getInstance(AnalyzeReportActivity.this);

        Toolbar mTopToolbar = findViewById(R.id.analyze_toolbar);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setNavigationIcon(R.drawable.action_navigation_arrow);
        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * manipula os clicks nos botões
     * @param v view que contém o ID do botão
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.COHERENCE_INFO:
                startActivity(new Intent(AnalyzeReportActivity.this, CoherenceActivity.class));
                break;

            case R.id.SALINITY_INFO:
                startActivity(new Intent(AnalyzeReportActivity.this, SalinityActivity.class));

                break;

            case R.id.SAR_INFO:
                startActivity(new Intent(AnalyzeReportActivity.this, SarActivity.class));

                break;

            case R.id.TOXITY_INFO:
                startActivity(new Intent(AnalyzeReportActivity.this, ToxityActivity.class));

                break;

            case R.id.PH_INFO:
                startActivity(new Intent(AnalyzeReportActivity.this, PhActivity.class));

                break;

        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case INSERT_SELECT_DATE:

                if(resultCode == RESULT_OK) {
                    report.setDate(data.getLongExtra("date",  System.currentTimeMillis()));

                    Intent intent = new Intent(AnalyzeReportActivity.this, ListSourcesActivity.class);
                    intent.putExtra(CALLING_ACTIVITY, ANALYZE_REPORT_ACTIVITY);
                    startActivityForResult(intent, INSERT_SELECT_SOURCE);

                }
            break;

            case INSERT_SELECT_SOURCE:

                if(resultCode == RESULT_OK) {
                    report.setSouId(data.getIntExtra("sourceID", 0));
                    report.setSouName(data.getStringExtra("sourceName"));

                    try{
                        // Pronto para armazenar no BD
                        new AsyncInsert().execute();
                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(AnalyzeReportActivity.this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
                    }
                }
            break;

            case UPDATE_SELECT_SOURCE:

                if(resultCode == RESULT_OK) {
                    report.setSouId(data.getIntExtra("sourceID", 0));
                    report.setSouName(data.getStringExtra("sourceName"));
                    Toast.makeText(this, R.string.localizacao_atualizada, Toast.LENGTH_LONG).show();

                }
            break;

            case UPDATE_SELECT_DATE:

                if(resultCode == RESULT_OK) {
                    report.setDate(data.getLongExtra("date", System.currentTimeMillis()));
                    Toast.makeText(this, R.string.data_atualizada, Toast.LENGTH_LONG).show();

                }
            break;


        }
    }

    private void removeNaNFromSAR(){
        //tentativa de corrigir o problema de salvar o relatório
        if(Double.isNaN(report.getCorrectedSar()))
            report.setCorrectedSar(0.0);

        if(Double.isNaN(report.getNormalSar()))
            report.setNormalSar(0.0);
    }

    //TODO resolver o problema do leak https://stackoverflow.com/questions/44309241/warning-this-asynctask-class-should-be-static-or-leaks-might-occur
    private class AsyncInsert extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            removeNaNFromSAR();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.reportDao().insert(report);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(AnalyzeReportActivity.this, R.string.relatorio_salvo, Toast.LENGTH_SHORT).show();
        }
    }

    private class AsyncUpdate extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            removeNaNFromSAR();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            appDatabase.reportDao().update(report);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // passar o relatorio para a activity de detalhes junto com uma flag apontando que vem da atualização
            // preciso limpar o histórico para o usuário não apertar back e ver os dados desatualizados
            Toast.makeText(AnalyzeReportActivity.this, R.string.relatorio_atualizado, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AnalyzeReportActivity.this, ReportDetailsActivity.class);
            intent.putExtra(REPORT, report);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//app deu crash, o log do erro pediu essa flag.
            startActivity(intent);
        }
    }


}
