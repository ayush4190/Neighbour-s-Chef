package com.example.myapplication.ui.fragment.menu.restoftheweek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.databinding.FragmentRestOfTheWeekTabBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.menu.MenuAdapter;
import com.example.myapplication.util.android.base.BaseFragment;
import com.example.myapplication.util.common.State;

import java.util.ArrayList;
import java.util.List;

public class RestoftheWeekFragment extends BaseFragment<FragmentRestOfTheWeekTabBinding> {
    private List<Product> products = new ArrayList<>();
    private MenuAdapter adapter;

    private RestOfTheWeekViewModel viewModel;

    public RestoftheWeekFragment() {}

    public static RestoftheWeekFragment newInstance() {
        return new RestoftheWeekFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestOfTheWeekTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RestOfTheWeekViewModel.class);

        adapter = new MenuAdapter(products, requireActivity().getSupportFragmentManager());
        binding.recyclerviewNonveg.setAdapter(adapter);
        binding.recyclerviewNonveg.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewNonveg.setHasFixedSize(false);

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