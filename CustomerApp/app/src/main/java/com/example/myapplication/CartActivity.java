package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if(findViewById(R.id.cart_container) != null) {
            CartFragment cartFragment = new CartFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.cart_container, cartFragment).commit();
        }
    }
}
