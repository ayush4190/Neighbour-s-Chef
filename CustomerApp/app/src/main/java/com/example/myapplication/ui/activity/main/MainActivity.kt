package com.example.myapplication.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.activity.cart.CartActivity
import com.example.myapplication.ui.activity.main.MainActivity
import com.example.myapplication.ui.fragment.help.HelpFragment

import com.example.myapplication.ui.fragment.home.HomeFragment
import com.example.myapplication.ui.fragment.profile.ProfileFragment

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {
    private var binding: ActivityMainBinding? = null
    var cartNumber: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        city = "Hyderabad"
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        cartNumber = findViewById(R.id.cart_item_number)
        //        cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(this).getSize()));
        fab.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
            )
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val navHeaderView =
            navigationView.inflateHeaderView(R.layout.nav_header_main)
        val header_mobile = navHeaderView.findViewById<TextView>(R.id.nav_mobile)
        val header_name = navHeaderView.findViewById<TextView>(R.id.nav_name)
        if (findViewById<View?>(R.id.main_fragment_container) != null) {
            val homeFragment = HomeFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, homeFragment).commit()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        val transaction =
            supportFragmentManager.beginTransaction()
        when (id) {
            R.id.nav_home -> {
                val homeFragment = HomeFragment.newInstance()
                transaction.replace(R.id.main_fragment_container, homeFragment).commit()
            }
            R.id.nav_addr -> {
            }
            R.id.nav_profile -> {
                val profileFragment = ProfileFragment.newInstance()
                transaction.replace(R.id.main_fragment_container, profileFragment).commit()
            }
            R.id.nav_history -> {
//                val historyFragment = HistoryFragment.newInstance()
//                transaction.replace(R.id.main_fragment_container, historyFragment).commit()
            }
            R.id.nav_track -> {
//                val trackFragment = TrackFragment.newInstance()
//                transaction.replace(R.id.main_fragment_container, trackFragment).commit()
            }
            R.id.nav_help -> {
                val helpFragment = HelpFragment.newInstance()
                transaction.replace(R.id.main_fragment_container, helpFragment).commit()
            }
            R.id.nav_rate -> {
            }
            R.id.nav_logout -> {
            }
            else -> {
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        var city: String? = null
    }
}