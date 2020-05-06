package com.example.myapplication.ui.activity.login

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private var binding: ActivitySigninBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding!!.getRoot())

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        val mSectionsPagerAdapter =
            SectionsPagerAdapter(
                supportFragmentManager
            )

        // Set up the ViewPager with the sections adapter.
        binding!!.container.adapter = mSectionsPagerAdapter
        binding!!.tabs.setupWithViewPager(binding!!.container)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_sign_in, menu)
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
}