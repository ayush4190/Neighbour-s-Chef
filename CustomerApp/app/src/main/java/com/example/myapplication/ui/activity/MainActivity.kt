package com.example.myapplication.ui.activity

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
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Cart
import com.example.myapplication.model.Product
import com.example.myapplication.util.common.EXTRA_PRODUCT
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
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
            setOf(R.id.nav_home, R.id.nav_help, R.id.nav_profile, R.id.nav_registration),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
//        binding.navView.setNavigationItemSelectedListener(this)

        initViews()
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        item.isChecked = true
//
//        when (item.itemId) {
//            R.id.home -> navController.navigate(MobileNavigationDirections.navigateToHome())
//            R.id.profile -> navController.navigate(MobileNavigationDirections.navigateToProfile())
//            R.id.history -> toast(this, "Coming Soon")
//            R.id.track -> toast(this, "Coming Soon")
//            R.id.addr -> toast(this, "Coming Soon")
//            R.id.help -> navController.navigate(MobileNavigationDirections.navigateToHelp())
//            R.id.rate -> toast(this, "Coming Soon")
//            R.id.logout -> toast(this, "Coming Soon")
//            else -> throw IllegalArgumentException("Unknown item: $item")
//        }
//        binding.drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

    private fun initViews() {
        city = "Hyderabad"

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
                    supportActionBar?.title = getString(R.string.app_name)
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
                    supportActionBar?.title = getString(R.string.title_cart)
                    binding.layoutAppBar.fab.visibility = View.GONE
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
                R.id.nav_registration -> {
                    supportActionBar?.title = getString(R.string.app_name)
                    binding.layoutAppBar.fab.visibility = View.GONE
                    binding.navView.visibility = View.GONE
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    companion object {
        var city: String? = null
    }
}