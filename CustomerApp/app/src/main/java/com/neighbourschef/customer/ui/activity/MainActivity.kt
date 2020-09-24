package com.neighbourschef.customer.ui.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.ActivityMainBinding
import com.neighbourschef.customer.model.Product
import com.neighbourschef.customer.util.android.getCart
import com.neighbourschef.customer.util.common.EXTRA_PRODUCT
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()

    private lateinit var binding: ActivityMainBinding
    private val navController: NavController by lazy(LazyThreadSafetyMode.NONE) {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    private val appBarConfiguration by lazy(LazyThreadSafetyMode.NONE) {
        AppBarConfiguration(
            setOf(R.id.nav_menu, R.id.nav_registration, R.id.nav_orders)
        )
    }
    lateinit var googleSignInClient: GoogleSignInClient

    private val sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { sp, key ->
        if (key == Firebase.auth.uid) {
            val cart = getCart(sp, Firebase.auth.uid)
            binding.fabCart.text = if (cart.isEmpty()) {
                binding.fabCart.isExtended = false
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
        setSupportActionBar(binding.toolbar)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        binding.navView.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId) {
                R.id.nav_menu -> {
                    navController.navigate(MobileNavigationDirections.navigateToMenu())
                    true
                }
                R.id.nav_orders -> {
                    navController.navigate(MobileNavigationDirections.navigateToOrders())
                    true
                }
                else -> false
            }
        }

        val cart = getCart(sharedPreferences, Firebase.auth.uid)
        binding.fabCart.text = if (cart.isEmpty()) {
            binding.fabCart.isExtended = false
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

    override fun onResume() {
        super.onResume()

        val cart = getCart(sharedPreferences, Firebase.auth.uid)
        binding.fabCart.text = if (cart.isEmpty()) {
            binding.fabCart.isExtended = false
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

        binding.fabCart.setOnClickListener {
            navController.navigate(MobileNavigationDirections.navigateToCart())
        }

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.nav_splash -> {
                    binding.fabCart.isVisible = false
                    binding.navView.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.appbar.isVisible = false
                }
                R.id.nav_menu -> {
                    binding.fabCart.isVisible = true
                    binding.navView.isVisible = true
                    binding.appbar.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.nav_item_detail -> {
                    binding.fabCart.isVisible = true
                    binding.navView.isVisible = true
                    binding.appbar.isVisible = true
                    supportActionBar?.title = arguments?.getParcelable<Product>(EXTRA_PRODUCT)?.name
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_cart -> {
                    binding.fabCart.isVisible = false
                    binding.navView.isVisible = true
                    binding.appbar.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_registration -> {
                    binding.fabCart.isVisible = false
                    binding.navView.isVisible = false
                    binding.appbar.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.nav_profile -> {
                    binding.fabCart.isVisible = false
                    binding.navView.isVisible = true
                    binding.appbar.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_orders -> {
                    binding.fabCart.isVisible = true
                    binding.navView.isVisible = true
                    binding.appbar.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }
}
