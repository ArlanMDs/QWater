package br.com.ufersa.qwater.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.ReportAdapter;
import br.com.ufersa.qwater.beans.Report;
import br.com.ufersa.qwater.database.AppDatabase;

public class ListReportsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_reports, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.reports_list));
        this.view = view;

        initiate();

        // Popula a lista
        new AsyncRead().execute();

    }

    private void initiate(){

        recyclerView = view.findViewById(R.id.WATER_REPORT_RECYCLER_VIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //prepara o bd
        appDatabase = AppDatabase.getInstance(getActivity());

        FloatingActionButton fab = view.findViewById(R.id.FAB_LIST_WATER_REPORTS);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new CreateReportFragment(), "CREATE_REPORT")
                        .commit();

            }
        });

    }

    private class AsyncRead extends AsyncTask<Void, Void, List<Report>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Report> doInBackground(Void... voids) {

            return appDatabase.reportDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Report> reports) {
            RecyclerView.Adapter adapter = new ReportAdapter(view.getContext(), reports);
            recyclerView.setAdapter(adapter);

        }
    }
}
