package com.neighbourschef.customer.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.ActivityMainBinding
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    val cart by instance<Cart>()

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.layoutAppBar.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_help, R.id.nav_profile, R.id.nav_registration, R.id.nav_history),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.setNavigationItemSelectedListener {
            binding.drawerLayout.closeDrawers()
            it.isChecked = true
            when(it.itemId) {
                R.id.nav_home -> {
                    navController.navigate(MobileNavigationDirections.navigateToHome())
                    true
                }
                R.id.nav_profile -> {
                    navController.navigate(MobileNavigationDirections.navigateToProfile())
                    true
                }
                R.id.nav_help -> {
                    navController.navigate(MobileNavigationDirections.navigateToHelp())
                    true
                }
                R.id.nav_history -> {
                    navController.navigate(MobileNavigationDirections.navigateToHistory())
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
        binding.layoutAppBar.fab.text = if (cart.isEmpty()) {
            ""
        } else {
            getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
        }
        binding.layoutAppBar.fab.setOnClickListener {
            navController.navigate(MobileNavigationDirections.navigateToCart())
        }

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.nav_home -> {
                    binding.layoutAppBar.fab.visibility = View.VISIBLE
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_item_detail -> {
                    supportActionBar?.title = arguments?.getParcelable<Product>(EXTRA_PRODUCT)?.name
                    binding.layoutAppBar.fab.visibility = View.VISIBLE
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_cart -> {
                    binding.layoutAppBar.fab.visibility = View.GONE
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_registration -> {
                    binding.layoutAppBar.fab.visibility = View.GONE
                    binding.navView.visibility = View.GONE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.nav_profile -> {
                    binding.layoutAppBar.fab.visibility = View.GONE
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }
}