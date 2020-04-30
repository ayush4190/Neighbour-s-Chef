package com.example.vendorsapp.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.vendorsapp.databinding.FragmentHomepageBinding;
import com.example.vendorsapp.model.OrderDetail;
import com.example.vendorsapp.util.android.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends BaseFragment<FragmentHomepageBinding> {
    private List<OrderDetail> mOrderList = new ArrayList<>();
    private OrderAdapter adapter ;

    private HomepageFragment() {}

    public static HomepageFragment newInstance() {
        return new HomepageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomepageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new OrderAdapter(mOrderList, requireActivity());
        binding.recyclerviewOrder.setAdapter(adapter);
        binding.recyclerviewOrder.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.recyclerviewOrder.setHasFixedSize(false);
    }
}
