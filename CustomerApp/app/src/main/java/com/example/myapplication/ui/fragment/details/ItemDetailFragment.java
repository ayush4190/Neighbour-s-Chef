package com.example.myapplication.ui.fragment.details;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentItemDetailBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.activity.cart.CartActivity;
import com.example.myapplication.util.android.base.BaseFragment;

import static com.example.myapplication.util.common.Constants.EXTRA_PRODUCT;

public class ItemDetailFragment extends BaseFragment<FragmentItemDetailBinding> {
    private Product product;
    final private String TAG = "FoodDetail";

    public ItemDetailFragment() {}

    public static ItemDetailFragment newInstance(Product product) {
        ItemDetailFragment fragment = new ItemDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        product = requireArguments().getParcelable(EXTRA_PRODUCT);
        initFoodInfo();

        setButtonListener();
    }

    private void initFoodInfo() {
        binding.foodDetailId.setText(product.getId());
        binding.foodDetailPrice.setText(product.getPrice());
        binding.foodDetailCollapsingToolbar.setTitle(product.getName());
    }

    private void setButtonListener() {
        binding.foodDetailAdd.setOnClickListener(view -> {

          //  ShoppingCartItem.getInstance(getContext()).addToCart(food);
            TextView cartNumber = requireActivity().findViewById(R.id.cart_item_number);
         //   cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getSize()));


            new AlertDialog.Builder(getActivity()).setTitle("Successful!").setIcon(
                    android.R.drawable.ic_dialog_info)
                    .setMessage("Add 1 " + "mItemlist.getProduct_name()" + " to cart!")
                    .setPositiveButton("Jump to cart", (dialogInterface, i) -> {
                        Intent cartAct = new Intent(getActivity(), CartActivity.class);
                        startActivity(cartAct);
                    })
                    .setNegativeButton("Continue", null).show();
        });
    }
}