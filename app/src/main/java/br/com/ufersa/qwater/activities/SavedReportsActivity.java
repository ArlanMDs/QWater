package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Read;
import br.com.ufersa.qwater.models.CustomReportAdapter;
import br.com.ufersa.qwater.models.Report;
//TODO sempre que a lista é exibida está sendo feito um acesso ao banco,
// o delete poderia ser feito sem precisar acessar novamente o banco, bastaria apenas remover a coluna da lista após o delete, sem usar o refresh na activity
public class SavedReportsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_reports);
        listReports();
    }

    private void listReports() {
        ListView listView = (ListView)findViewById(R.id.displayListView);

        final ArrayList<Report> reports = new Read().getReports();

        // Create The Adapter with passing ArrayList and Activity as parameter
        CustomReportAdapter adapter = new CustomReportAdapter(reports, this);

        // Set The Adapter
        listView.setAdapter(adapter);

        // register onClickListener to handle click events on each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                Intent intent = new Intent(SavedReportsActivity.this, ReportDetailsActivity.class);
                intent.putExtra("report", reports.get(position));
                startActivity(intent);
            }
        });
    }

}
