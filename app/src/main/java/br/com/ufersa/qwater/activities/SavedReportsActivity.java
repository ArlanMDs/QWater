package br.com.ufersa.qwater.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.database.Read;
import br.com.ufersa.qwater.models.CustomReportAdapter;
import br.com.ufersa.qwater.models.Report;

public class SavedReportsActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_reports);
        findViewsIds();
        listReports();
    }

    private void findViewsIds() {
        listView = (ListView)findViewById(R.id.displayListView);

    }

    private void listReports() {

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
                Report selectedReport = reports.get(position);
                Toast.makeText(getApplicationContext(), "Report Selected : " + selectedReport,   Toast.LENGTH_LONG).show();
            }
        });
    }

}
