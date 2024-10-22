package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.beans.Report;
import br.com.ufersa.qwater.beans.Source;
import br.com.ufersa.qwater.fragments.CreateReportFragment;
import br.com.ufersa.qwater.fragments.CreateSourceFragment;
import br.com.ufersa.qwater.fragments.HomeFragment;
import br.com.ufersa.qwater.fragments.ListReportsFragment;
import br.com.ufersa.qwater.fragments.ListSourcesFragment;
import br.com.ufersa.qwater.info_activities.AboutActivity;

import static br.com.ufersa.qwater.util.Flags.DELETE_REPORT;
import static br.com.ufersa.qwater.util.Flags.DELETE_SOURCE;
import static br.com.ufersa.qwater.util.Flags.GOING_TO;
import static br.com.ufersa.qwater.util.Flags.REPORT;
import static br.com.ufersa.qwater.util.Flags.SEE_UPDATED_SOURCE;
import static br.com.ufersa.qwater.util.Flags.SOURCE;
import static br.com.ufersa.qwater.util.Flags.UPDATE_REPORT;
import static br.com.ufersa.qwater.util.Flags.UPDATE_SOURCE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private boolean isUpdatingReportOrSource = false; // Para controle do onBackPressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Se tem a flag going to e ela for para atualizar, então o extra contém um relatório para ser atualizado
        // Se a flag for de delete, a intenção é colocar o fragmento de lista de reports para a lista ser atualizada
        if(getIntent().hasExtra(GOING_TO)) {
            if (getIntent().getIntExtra(GOING_TO, 0) == UPDATE_REPORT) {
                Report report = getIntent().getParcelableExtra(REPORT);
                isUpdatingReportOrSource = true;
                passReportFromMainToFragment(report);
            }
            else if(getIntent().getIntExtra(GOING_TO, 0) == DELETE_REPORT){
                displaySelectedScreen(R.id.LIST_REPORTS);
                navigationView.setCheckedItem(R.id.LIST_REPORTS);
            }
            else if(getIntent().getIntExtra(GOING_TO, 0) == UPDATE_SOURCE){
                Source source = getIntent().getParcelableExtra(SOURCE);
                isUpdatingReportOrSource = true;
                passSourceFromMainToFragment(source);
            }
            else if(getIntent().getIntExtra(GOING_TO,0) == DELETE_SOURCE){
                displaySelectedScreen(R.id.LIST_SOURCES);
                navigationView.setCheckedItem(R.id.LIST_SOURCES);

            }else if(getIntent().getIntExtra(GOING_TO,0) == SEE_UPDATED_SOURCE){
                displaySelectedScreen(R.id.LIST_SOURCES);
                navigationView.setCheckedItem(R.id.LIST_SOURCES);

            }
        }
        else {
            //add this line to display menu when the activity is loaded
            displaySelectedScreen(R.id.HOME);
            navigationView.setCheckedItem(R.id.HOME);
            //abrir o menu de navegação
        }
    }

    /*
    SEND DATA TO FRAGMENT
     */
    private void passReportFromMainToFragment(Report report) {

        //PACK DATA IN A BUNDLE
        Bundle bundle = new Bundle();
        bundle.putParcelable(REPORT, report);

        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        Fragment fragment = new CreateReportFragment();
        fragment.setArguments(bundle);

        //THEN NOW SHOW OUR FRAGMENT
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "UPDATE_REPORT");
        ft.commit();
    }

    private void passSourceFromMainToFragment(Source source) {

        //PACK DATA IN A BUNDLE
        Bundle bundle = new Bundle();
        bundle.putParcelable(SOURCE, source);

        //PASS OVER THE BUNDLE TO OUR FRAGMENT
        Fragment fragment = new CreateSourceFragment();
        fragment.setArguments(bundle);

        //THEN NOW SHOW OUR FRAGMENT
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "UPDATE_SOURCE");
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        String tag;

        // ATENÇÃO: O floating button dentro dos fragmentos de listar também estão usando tag, pois eles fazem commit dentro dos seus click listeners.
        // Caso mude aqui, também deve ser mudado lá!
        if (itemId == R.id.HOME) {
            fragment = new HomeFragment();
            tag = "HOME";
        } else if (itemId == R.id.CREATE_SOURCE) {
            fragment = new CreateSourceFragment();
            tag = "CREATE_SOURCE";
        } else if (itemId == R.id.CREATE_REPORT) {
            fragment = new CreateReportFragment();
            tag = "CREATE_REPORT";
        } else if (itemId == R.id.LIST_REPORTS) {
            fragment = new ListReportsFragment();
            tag = "LIST_REPORTS";
        } else if (itemId == R.id.LIST_SOURCES) {
            fragment = new ListSourcesFragment();
            tag = "LIST_SOURCES";
        } else {
            tag = "NULL";
        }


        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment, tag);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment currentFragment = fragmentManager.findFragmentById(R.id.content_frame);

            // se estiver atualizando um relatório, deve voltar para a activity de detalhes
            if (isUpdatingReportOrSource)
                super.onBackPressed();

            // Se o fragmento atual exibido não for a home, o botão voltar direciona para ela
            else if (!currentFragment.getTag().equals("HOME")) {
                displaySelectedScreen(R.id.HOME);
                navigationView.setCheckedItem(R.id.HOME);
            }else
                super.onBackPressed();
        }
    }
}
