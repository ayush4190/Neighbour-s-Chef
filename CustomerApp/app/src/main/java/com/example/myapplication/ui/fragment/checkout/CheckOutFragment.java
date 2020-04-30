package com.example.myapplication.ui.fragment.checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.FragmentCheckoutBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.util.android.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.util.common.Constants.EXTRA_PRODUCT;

public class CheckOutFragment extends BaseFragment<FragmentCheckoutBinding> {
    private List<Product> products = new ArrayList<>();
    private CheckOutAdapter adapter;

    private CheckOutFragment() {}

    public static CheckOutFragment newInstance(@Nullable final Product product) {
        CheckOutFragment fragment = new CheckOutFragment();
        if (product != null) {
            Bundle args = new Bundle();
            args.putParcelable(EXTRA_PRODUCT, product);
            fragment.setArguments(args);
        }
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CheckOutAdapter(products);
        binding.recyclerCheckout.setAdapter(adapter);
        binding.recyclerCheckout.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerCheckout.setHasFixedSize(false);
    }
}
