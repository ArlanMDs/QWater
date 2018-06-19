package br.com.ufersa.qwater.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.ufersa.qwater.Fragments.CreateReportFragment;
import br.com.ufersa.qwater.Fragments.CreateSourceFragment;
import br.com.ufersa.qwater.Fragments.ListReportsFragment;
import br.com.ufersa.qwater.Fragments.ListSourcesFragment;
import br.com.ufersa.qwater.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded
        //displaySelectedScreen(R.id.CREATE_SOURCE);
        //TODO fazer fragmento da tela inicial
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.CREATE_SOURCE:
                fragment = new CreateSourceFragment();
                break;
            case R.id.CREATE_REPORT:
                fragment = new CreateReportFragment();
                break;
            case R.id.LIST_REPORTS:
                fragment = new ListReportsFragment();
                break;
            case R.id.LIST_SOURCES:
                fragment = new ListSourcesFragment();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//        //TODO transformar as activities em fragmentos para popular um framelayout na main activity
//        if (id == R.id.CREATE_SOURCE) {
//            startActivity(new Intent(getApplicationContext(), CreateWaterSourceFragment.class));
//
//        } else if (id == R.id.CREATE_REPORT) {
//            startActivity(new Intent(getApplicationContext(), CreateWaterReportActivity.class));
//
//        } else if (id == R.id.LIST_REPORTS) {
//            startActivity(new Intent(getApplicationContext(), ListWaterReportsActivity.class));
//
//        } else if (id == R.id.LIST_SOURCES) {
//            startActivity(new Intent(getApplicationContext(), ListWaterSourcesActivity.class));
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//        //calling the method displayselectedscreen and passing the id of selected menu
//        displaySelectedScreen(item.getItemId());
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);

        displaySelectedScreen(item.getItemId());

        return true;
    }
}
