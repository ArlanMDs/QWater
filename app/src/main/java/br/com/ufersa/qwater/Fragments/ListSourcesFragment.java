package br.com.ufersa.qwater.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.WaterSourceAdapter;
import br.com.ufersa.qwater.beans.WaterSource;
import br.com.ufersa.qwater.database.AppDatabase;

public class ListSourcesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private AppDatabase appDatabase;
    private Toolbar toolbar;
    private final static int REQUEST_CODE_ANALISE_ACTIVITY = 1;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_sources, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Lista de fontes");
        this.view = view;

        initiate();

        //Popula a lista
        new AsyncRead().execute();

    }

    private void initiate(){

        recyclerView = view.findViewById(R.id.WATER_SOURCE_RECYCLER_VIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //prepara o bd
        appDatabase = AppDatabase.getInstance(getActivity());

        fab = view.findViewById(R.id.FAB_LIST_WATERSOURCES);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, new CreateSourceFragment(), "CREATE_SOURCE")
                        .commit();
            }
        });

    }

    private class AsyncRead extends AsyncTask<Void, Void, List<WaterSource>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<WaterSource> doInBackground(Void... voids) {

            return appDatabase.waterSourceDao().getAll();
        }

        @Override
        protected void onPostExecute(List<WaterSource> waterSources) {

            RecyclerView.Adapter adapter = new WaterSourceAdapter(getCodeOfIncomingIntent(), getActivity(), waterSources);
            recyclerView.setAdapter(adapter);

        }
    }

    private int getCodeOfIncomingIntent(){
        if(getActivity().getIntent().hasExtra("callingActivity")) {

            int code = getActivity().getIntent().getIntExtra("callingActivity",0);

            // Caso a activity que chamou seja a analize, esconde o fab para que não vá para a activity de criar
            // E troca o título da activity
            if(code == REQUEST_CODE_ANALISE_ACTIVITY ) {
                fab.setVisibility(View.GONE);
                toolbar.setTitle(R.string.escolher_fonte);
            }
            return code;
        }
        else{
            return 0;
        }
    }


}
