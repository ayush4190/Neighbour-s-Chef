package com.example.myapplication.ui.fragment.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentCartBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.checkout.CheckOutFragment;
import com.example.myapplication.util.android.base.BaseFragment;
import com.example.myapplication.util.android.base.CartSwipeCallback;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment<FragmentCartBinding> {
    private CartAdapter adapter;
    private List<Product> products = new ArrayList<>();
    private CartFragment() {}

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init(){
        // binding.cartTotalPrice.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getPrice()));

        adapter = new CartAdapter();
        initSwipe();
        binding.recyclerviewCart.setAdapter(adapter);
        binding.recyclerviewCart.setHasFixedSize(false);
        binding.recyclerviewCart.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.cartBack.setOnClickListener(view -> requireActivity().finish());

        binding.cartCheckout.setOnClickListener(view -> {

           CheckOutFragment checkOutFragment= new CheckOutFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.main_fragment_container, checkOutFragment)
                    .addToBackStack(CartFragment.class.getName())
                    .commit();


        });
    }

    private void initSwipe() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CartSwipeCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerviewCart);
    }
}
