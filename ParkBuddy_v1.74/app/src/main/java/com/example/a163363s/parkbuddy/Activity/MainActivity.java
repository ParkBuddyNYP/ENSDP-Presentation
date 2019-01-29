package com.example.a163363s.parkbuddy.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ViewFlipper;

import com.example.a163363s.parkbuddy.HomeFragment;
import com.example.a163363s.parkbuddy.LocateFragment;
import com.example.a163363s.parkbuddy.R;
import com.example.a163363s.parkbuddy.CameraFragment;
import com.example.a163363s.parkbuddy.ReportFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    ViewFlipper v_Flipper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }


    protected void showCameraFragment()
    {
        CameraFragment newFragmentToShow = new CameraFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, newFragmentToShow)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_find:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new LocateFragment()).commit();
                break;


            case R.id.nav_camera:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new CameraFragment()).commit();
                break;

            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportFragment()).commit();
                break;

           // case R.id.nav_settings:
                //Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                //break;
            //case R.id.nav_send:
               // Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                //break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}