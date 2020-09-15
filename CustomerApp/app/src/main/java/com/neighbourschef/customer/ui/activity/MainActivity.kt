package com.neighbourschef.customer.ui.activity

import android.content.SharedPreferences
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
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.ActivityMainBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val sharedPreferences: SharedPreferences by inject()

    lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        findNavController(R.id.nav_host_fragment)
    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
        if (key == PREFERENCE_CART) {
            val cart = getCart(sp)
            binding.layoutAppBar.fabCart.text = if (cart.isEmpty()) {
                binding.layoutAppBar.fabCart.isExtended = false
                ""
            } else {
                getString(
                    R.string.set_items,
                    cart.size(),
                    resources.getQuantityString(R.plurals.items, cart.size())
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.layoutAppBar.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_help, R.id.nav_profile, R.id.nav_registration, R.id.nav_orders),
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
                R.id.nav_orders -> {
                    navController.navigate(MobileNavigationDirections.navigateToOrders())
                    true
                }
                else -> false
            }
        }

        val cart = getCart(sharedPreferences)
        binding.layoutAppBar.fabCart.text = if (cart.isEmpty()) {
            binding.layoutAppBar.fabCart.isExtended = false
            ""
        } else {
            getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
        }
        setupListeners()
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

    override fun onResume() {
        super.onResume()

        val cart = getCart(sharedPreferences)
        binding.layoutAppBar.fabCart.text = if (cart.isEmpty()) {
            binding.layoutAppBar.fabCart.isExtended = false
            ""
        } else {
            getString(
                R.string.set_items,
                cart.size(),
                resources.getQuantityString(R.plurals.items, cart.size())
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    private fun setupListeners() {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        binding.layoutAppBar.fabCart.setOnClickListener {
            navController.navigate(MobileNavigationDirections.navigateToCart())
        }

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.nav_home -> {
                    binding.layoutAppBar.fabCart.isVisible = true
                    binding.navView.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_item_detail -> {
                    supportActionBar?.title = arguments?.getParcelable<Product>(EXTRA_PRODUCT)?.name
                    binding.layoutAppBar.fabCart.isVisible = true
                    binding.navView.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_cart -> {
                    binding.layoutAppBar.fabCart.isVisible = false
                    binding.navView.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_registration -> {
                    binding.layoutAppBar.fabCart.isVisible = false
                    binding.navView.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.nav_profile -> {
                    binding.layoutAppBar.fabCart.isVisible = false
                    binding.navView.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }
}
