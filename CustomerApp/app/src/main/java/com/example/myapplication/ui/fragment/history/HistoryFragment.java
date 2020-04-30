package com.example.myapplication.ui.fragment.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.FragmentHistoryBinding;
import com.example.myapplication.model.Order;
import com.example.myapplication.util.android.base.BaseFragment;

import java.util.ArrayList;

public class HistoryFragment extends BaseFragment<FragmentHistoryBinding> {
    private ArrayList<Order> orders = new ArrayList<>();
    private HistoryAdapter adapter;

    private HistoryFragment() {}

    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().setTitle("History");

        if (orders.size()==0){

        }

        adapter = new HistoryAdapter(orders);
        binding.recyclerviewHistory.setAdapter(adapter);
        binding.recyclerviewHistory.setHasFixedSize(true);
        binding.recyclerviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}

