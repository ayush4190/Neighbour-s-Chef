package com.example.vendorsapp.ui.fragment.order;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vendorsapp.R;
import com.example.vendorsapp.databinding.FragmentOrderListBinding;
import com.example.vendorsapp.model.Product;
import com.example.vendorsapp.util.android.base.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderListFragment extends BaseFragment<FragmentOrderListBinding> {
    private OrderItemListAdapter adapter;
    private List<Product> mItemList = new ArrayList<>();

    public OrderListFragment() {}

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.Procced.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Order Proccesing Completed ",Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(),"Check the History section to see processed requests",Toast.LENGTH_SHORT).show();

                requireActivity().getSupportFragmentManager().popBackStackImmediate (null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                FloatingActionButton floatingActionButton = requireActivity().findViewById(R.id.fab);
                floatingActionButton.setVisibility(View.VISIBLE);
            }
        });
//
        adapter = new OrderItemListAdapter(mItemList);
//        getItemList();
        adapter.clear();
        binding.recyclerviewOrderlist.setAdapter(adapter);
        binding.recyclerviewOrderlist.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        binding.recyclerviewOrderlist.setHasFixedSize(false);
    }
}
