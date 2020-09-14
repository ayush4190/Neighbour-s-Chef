package com.neighbourschef.vendor.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.ActivityMainBinding
import com.neighbourschef.vendor.ui.fragment.order.OrdersFragmentDirections

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        findNavController(R.id.nav_host_fragment)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.layoutAppBar.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_orders),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            it.isChecked = true
            when (it.itemId) {
                R.id.nav_home -> {
                    navController.navigate(MobileNavigationDirections.navigateToOrders())
                    true
                }
                else -> false
            }
        }

        initViews()
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initViews() {
        binding.layoutAppBar.fab.setOnClickListener {
            navController.navigate(OrdersFragmentDirections.navigateToAddItems())
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home -> {
                    binding.layoutAppBar.fab.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_add_item -> {
                    binding.layoutAppBar.fab.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_order_details -> {
                    binding.layoutAppBar.fab.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }
}
