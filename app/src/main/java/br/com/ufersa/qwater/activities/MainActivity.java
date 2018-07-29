package br.com.ufersa.qwater.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ufersa.qwater.R;
import br.com.ufersa.qwater.fragments.CreateReportFragment;
import br.com.ufersa.qwater.fragments.CreateSourceFragment;
import br.com.ufersa.qwater.fragments.HomeFragment;
import br.com.ufersa.qwater.fragments.ListReportsFragment;
import br.com.ufersa.qwater.fragments.ListSourcesFragment;
import br.com.ufersa.qwater.info_activities.AboutActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu when the activity is loaded
        displaySelectedScreen(R.id.HOME);
        navigationView.setCheckedItem(R.id.HOME);


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
        switch (itemId) {
            case R.id.HOME:
                fragment = new HomeFragment();
                tag = "HOME";
                break;

            case R.id.CREATE_SOURCE:
                fragment = new CreateSourceFragment();
                tag = "CREATE_SOURCE";
                break;

            case R.id.CREATE_REPORT:
                fragment = new CreateReportFragment();
                tag = "CREATE_REPORT";
                break;

            case R.id.LIST_REPORTS:
                fragment = new ListReportsFragment();
                tag = "LIST_REPORTS";
                break;

            case R.id.LIST_SOURCES:
                fragment = new ListSourcesFragment();
                tag = "LIST_SOURCES";
                break;

            default:
                tag = "NULL";
                break;

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

            // Se o fragmento atual exibido não for a home, o botão voltar direciona para ela
            if (!currentFragment.getTag().equals("HOME")) {
                displaySelectedScreen(R.id.HOME);
                navigationView.setCheckedItem(R.id.HOME);
            }
            else
                super.onBackPressed();
        }
    }
}
