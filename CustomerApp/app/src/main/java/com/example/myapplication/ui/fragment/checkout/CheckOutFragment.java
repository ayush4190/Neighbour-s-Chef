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

public class CheckOutFragment extends BaseFragment<FragmentCheckoutBinding> {
    private List<Product> products = new ArrayList<>();
    private CheckOutAdapter adapter;
    public CheckOutFragment() {
    }

    public static CheckOutFragment newInstance(Product product) {
       CheckOutFragment fragment = new CheckOutFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(EXTRA_PRODUCT, product);
//        fragment.setArguments(args);
        return fragment;}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCheckoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CheckOutAdapter(products);
        binding.recyclerviewCheckout.setAdapter(adapter);
        binding.recyclerviewCheckout.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewCheckout.setHasFixedSize(false);

        adapter.setOnItemClickListener((view1, data) -> {
            int index = 0;
            for (int i = 0; i < products.size(); i++){
                if (products.get(i).getId().equals(data)){
                    index = i;
//                        itemInfo.putString("foodCat", mItemList.get(i).getCategory());
//                        itemInfo.putString("foodRec", foods.get(i).getRecepiee());
                    //  itemInfo.putString("foodImage", foods.get(i).getImageUrl());
                    break;
                }
            }

        });
    }
}
