package com.example.vendorsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class OrderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        init();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_home:
//                HomeFragment homeFragment = new HomeFragment();
//                transaction.replace(R.id.main_fragment_container, homeFragment).commit();
//                break;
            case R.id.nav_addr:
                break;
            case R.id.nav_profile:
//                ProfileFragment profileFragment = new ProfileFragment();
//                transaction.replace(R.id.main_fragment_container, profileFragment).commit();
//                break;
            case R.id.nav_history:
//                HistoryFragment historyFragment = new HistoryFragment();
//                transaction.replace(R.id.main_fragment_container, historyFragment).commit();
//                break;
            case R.id.nav_track:
//                TrackFragment trackFragment = new TrackFragment();
//                transaction.replace(R.id.main_fragment_container, trackFragment).commit();
//                break;
            case R.id.nav_help:
//                HelpFragment helpFragment = new HelpFragment();
//                transaction.replace(R.id.main_fragment_container, helpFragment).commit();
//                break;
            case R.id.nav_rate:
                break;
            case R.id.nav_logout:
//                SPManipulation.getInstance(this).clearSharedPreference();
//                LoginManager.getInstance().logOut();
//                if (mGoogleApiClient.isConnected()) {
////                    mGoogleApiClient.disconnect();
////                    // updateUI(false);
////                    System.err.println("LOG OUT ^^^^^^^^^^^^^^^^^^^^ SUCESS");
//                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                            new ResultCallback<Status>() {
//                                @Override
//                                public void onResult(Status status) {
//                                    // ...
//                                    Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
//                Intent splash = new Intent(this, SplashActivity.class);
//                startActivity(splash);
//                finish();
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }



    private  void init()
    {

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                AddItems additems = new AddItems();
                transaction.replace(R.id.main_fragment_container, additems).commit();
                fab.setVisibility(View.GONE);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView header_mobile = (TextView) navHeaderView.findViewById(R.id.nav_mobile);
        TextView header_name = (TextView) navHeaderView.findViewById(R.id.nav_name);


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
}
