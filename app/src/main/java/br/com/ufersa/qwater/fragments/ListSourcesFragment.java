package br.com.ufersa.qwater.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.adapters.SourceAdapter;
import br.com.ufersa.qwater.beans.Source;
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
        getActivity().setTitle(getString(R.string.sources_list));
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

    private class AsyncRead extends AsyncTask<Void, Void, List<Source>> {
        // referência https://stackoverflow.com/questions/11833978/asynctask-pass-two-or-more-values-from-doinbackground-to-onpostexecute
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<Source> doInBackground(Void... voids) {

            return appDatabase.sourceDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Source> sources) {

            RecyclerView.Adapter adapter = new SourceAdapter(getCodeOfIncomingIntent(), getActivity(), sources);
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
