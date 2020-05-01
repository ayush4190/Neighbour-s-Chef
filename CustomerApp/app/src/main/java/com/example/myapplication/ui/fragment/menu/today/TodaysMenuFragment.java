package com.example.myapplication.ui.fragment.menu.today;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.TodaysMenufragBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.menu.MenuAdapter;
import com.example.myapplication.util.android.base.BaseFragment;
import com.example.myapplication.util.common.State;

import java.util.ArrayList;
import java.util.List;

public class TodaysMenuFragment extends BaseFragment<TodaysMenufragBinding> {
    private List<Product> products = new ArrayList<>();
    private MenuAdapter adapter;

    private TodayMenuViewModel viewModel;

    public TodaysMenuFragment() {}

    public static TodaysMenuFragment newInstance() {
        return new TodaysMenuFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TodaysMenufragBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TodayMenuViewModel.class);

        adapter = new MenuAdapter(products, requireActivity().getSupportFragmentManager());
        binding.recyclerviewAll.setAdapter(adapter);
        binding.recyclerviewAll.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewAll.setHasFixedSize(false);

        observeChanges();
    }

    private void observeChanges() {
        viewModel.getProducts().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof State.Loading) {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show();
            } else if (state instanceof State.Success) {
                List<Product> data = (List<Product>) ((State.Success) state).getData();
                adapter.submitList(data, false);
            } else if (state instanceof State.Failure) {
                Toast.makeText(requireContext(), ((State.Failure) state).getReason(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}