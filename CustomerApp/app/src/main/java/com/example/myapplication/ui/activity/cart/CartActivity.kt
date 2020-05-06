package com.example.myapplication.ui.activity.cart

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCartBinding
import com.example.myapplication.ui.fragment.cart.CartFragment.Companion.newInstance

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        if (findViewById<View?>(R.id.cart_container) != null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.cart_container,
                    newInstance()
                )
                .commit()
        }
    }
}