package com.example.myapplication.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.ui.activity.cart.CartActivity;
import com.example.myapplication.ui.fragment.help.HelpFragment;
import com.example.myapplication.ui.fragment.history.HistoryFragment;
import com.example.myapplication.ui.fragment.home.HomeFragment;
import com.example.myapplication.ui.fragment.profile.ProfileFragment;
import com.example.myapplication.ui.fragment.track.TrackFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    public TextView cartNumber;

    public static String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
    }

    private void init() {
        city = "Hyderabad";
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        cartNumber = findViewById(R.id.cart_item_number);
//        cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(this).getSize()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this , drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView header_mobile = navHeaderView.findViewById(R.id.nav_mobile);
        TextView header_name = navHeaderView.findViewById(R.id.nav_name);

        if (findViewById(R.id.main_fragment_container) != null) {
            HomeFragment homeFragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, homeFragment).commit();
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (id) {
            case R.id.nav_home:
                HomeFragment homeFragment = HomeFragment.newInstance();
                transaction.replace(R.id.main_fragment_container, homeFragment).commit();
                break;
            case R.id.nav_addr:
                break;
            case R.id.nav_profile:
                ProfileFragment profileFragment = ProfileFragment.newInstance();
                transaction.replace(R.id.main_fragment_container, profileFragment).commit();
                break;
            case R.id.nav_history:
                HistoryFragment historyFragment = HistoryFragment.newInstance();
                transaction.replace(R.id.main_fragment_container, historyFragment).commit();
                break;
            case R.id.nav_track:
                TrackFragment trackFragment = TrackFragment.newInstance();
                transaction.replace(R.id.main_fragment_container, trackFragment).commit();
                break;
            case R.id.nav_help:
                HelpFragment helpFragment = HelpFragment.newInstance();
                transaction.replace(R.id.main_fragment_container, helpFragment).commit();
                break;
            case R.id.nav_rate:
                break;
            case R.id.nav_logout:

            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

