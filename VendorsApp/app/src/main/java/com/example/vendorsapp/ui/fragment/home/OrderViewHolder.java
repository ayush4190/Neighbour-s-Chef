package com.example.vendorsapp.ui.fragment.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.vendorsapp.R;
import com.example.vendorsapp.databinding.OrderViewBinding;
import com.example.vendorsapp.model.OrderDetail;
import com.example.vendorsapp.ui.activity.order.OrderActivity;
import com.example.vendorsapp.ui.fragment.order.OrderListFragment;
import com.example.vendorsapp.util.android.base.BaseViewHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

class OrderViewHolder extends BaseViewHolder<OrderViewBinding, OrderDetail> {
    private final Context context;

    OrderViewHolder(@NonNull OrderViewBinding binding, @Nullable Context context) {
        super(binding);
        if (context == null) {
            this.context = binding.getRoot().getContext();
        } else {
            this.context = context;
        }
    }

    @Override
    public void bindTo(@Nullable OrderDetail item) {
        binding.orderView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                OrderActivity activity = (OrderActivity) context;
                FloatingActionButton floatingActionButton = activity.findViewById(R.id.fab);
                floatingActionButton.setVisibility(View.GONE);
                Fragment myFragment = OrderListFragment.newInstance();
                activity.getSupportFragmentManager().beginTransaction().
                        replace(R.id.main_fragment_container, myFragment).
                        addToBackStack("Complete_order_list").commit();


            }
        });
    }
}
