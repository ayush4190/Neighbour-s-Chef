package com.example.myapplication.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.commit
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.ui.activity.cart.CartActivity
import com.example.myapplication.ui.fragment.help.HelpFragment
import com.example.myapplication.ui.fragment.home.HomeFragment
import com.example.myapplication.ui.fragment.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, KodeinAware {
    override val kodein by closestKodein()
    val cart: Cart by instance<Cart>()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        city = "Hyderabad"

        setSupportActionBar(binding.layoutAppbar.toolbar)

        binding.layoutAppbar.cartItemNumber.text = cart.size().toString()
        binding.layoutAppbar.fab.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            binding.layoutAppbar.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        val navHeaderView = binding.navView.inflateHeaderView(R.layout.nav_header_main)
        val header_mobile = navHeaderView.findViewById<TextView>(R.id.nav_mobile)
        val header_name = navHeaderView.findViewById<TextView>(R.id.nav_name)

        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, HomeFragment.newInstance())
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

    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        when (item.itemId) {
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
            R.id.nav_rate -> {}
            R.id.nav_logout -> {}
            else -> {}
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object {
        var city: String? = null
    }
}