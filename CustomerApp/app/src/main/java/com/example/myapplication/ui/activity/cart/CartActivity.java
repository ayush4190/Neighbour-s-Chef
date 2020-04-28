package com.example.myapplication.ui.activity.cart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.ui.fragment.cart.CartFragment;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (findViewById(R.id.cart_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(
                            R.id.cart_container,
                            CartFragment.newInstance()
                    )
                    .commit();
        }
    }
}
