package com.example.myapplication.ui.activity.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCartBinding
import com.example.myapplication.ui.fragment.cart.CartFragment

class CartActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            replace(R.id.cart_container, CartFragment.newInstance())
        }

    }
}