package com.example.vendorsapp.ui.activity.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.vendorsapp.R;
import com.example.vendorsapp.databinding.ActivityOrderBinding;
import com.example.vendorsapp.ui.fragment.additems.AddItemsFragment;
import com.example.vendorsapp.ui.fragment.home.HomepageFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class OrderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityOrderBinding binding;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    private  void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, AddItemsFragment.newInstance());
                transaction.addToBackStack ("add_item");
                transaction.commit();
                fab.setVisibility(View.GONE);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView header_mobile = navHeaderView.findViewById(R.id.nav_mobile);
        TextView header_name = navHeaderView.findViewById(R.id.nav_name);

        if (findViewById(R.id.main_fragment_container) != null) {
            HomepageFragment homeFragment = HomepageFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment ,"home_frag").commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen (GravityCompat.START)) {
            binding.drawerLayout.closeDrawer (GravityCompat.START);
        } else  {
            super.onBackPressed ();
            getSupportFragmentManager ().popBackStackImmediate (null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        HomepageFragment myFragment = (HomepageFragment)getSupportFragmentManager().findFragmentByTag("home_frag");

        if (myFragment != null && myFragment.isVisible()) {
            fab.setVisibility(View.VISIBLE);

        }
    }
}
