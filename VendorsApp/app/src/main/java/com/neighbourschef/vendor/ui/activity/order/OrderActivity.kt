package com.neighbourschef.vendor.ui.activity.order

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
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.ActivityOrderBinding
import com.neighbourschef.vendor.ui.fragment.additems.AddItemsFragment
import com.neighbourschef.vendor.ui.fragment.home.HomepageFragment

class OrderActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        val transaction = supportFragmentManager.beginTransaction()
        when (id) {
            R.id.nav_home, R.id.nav_addr -> {
            }
            R.id.nav_profile, R.id.nav_history, R.id.nav_track, R.id.nav_help, R.id.nav_rate -> {
            }
            R.id.nav_logout -> {
            }
            else -> {
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun init() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.appBarMain.fab.setOnClickListener(View.OnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment_container, AddItemsFragment.newInstance())
            transaction.addToBackStack("add_item")
            transaction.commit()
            binding.appBarMain.fab.setVisibility(View.GONE)
        })
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main)
        val header_mobile = navHeaderView.findViewById<TextView>(R.id.nav_mobile)
        val header_name = navHeaderView.findViewById<TextView>(R.id.nav_name)
        if (findViewById<View?>(R.id.main_fragment_container) != null) {
            val homeFragment = HomepageFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, homeFragment, "home_frag").commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            supportFragmentManager.popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        }
        val myFragment = supportFragmentManager.findFragmentByTag("home_frag") as HomepageFragment?
        if (myFragment != null && myFragment.isVisible) {
            binding.appBarMain.fab.visibility = View.VISIBLE
        }
    }
}
