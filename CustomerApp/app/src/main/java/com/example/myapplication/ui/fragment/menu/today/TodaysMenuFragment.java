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

import com.example.myapplication.R;
import com.example.myapplication.databinding.TodaysMenufragBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.details.ItemDetailFragment;
import com.example.myapplication.util.android.base.BaseFragment;
import com.example.myapplication.util.common.State;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class TodaysMenuFragment extends BaseFragment<TodaysMenufragBinding> {
    private List<Product> products = new ArrayList<>();
    private TodaysMenuAdapter adapter;

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

        adapter = new TodaysMenuAdapter(products);
        binding.recyclerviewAll.setAdapter(adapter);
        binding.recyclerviewAll.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewAll.setHasFixedSize(false);

        adapter.setOnItemClickListener((view1, data) -> {
            int index = 0;
            for (int i = 0; i < products.size(); i++){
                if (products.get(i).getId().equals(data)){
                    index = i;
                    break;
                }
            }
            ItemDetailFragment foodDetailFragment = ItemDetailFragment.newInstance(products.get(index));

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.main_fragment_container, foodDetailFragment)
                    .addToBackStack(TodaysMenuFragment.class.getName())
                    .commit();
        });

        observeChanges();
    }

    private void observeChanges() {
        viewModel.getProduct().observe(getViewLifecycleOwner(), state -> {
            if (state instanceof State.Loading) {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show();
            } else if (state instanceof State.Success) {
                Pair<String, Product> pair = (Pair<String, Product>) ((State.Success) state).getData();
                assert pair != null;
                adapter.addItem(pair.getSecond(), pair.getFirst());
            } else {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}