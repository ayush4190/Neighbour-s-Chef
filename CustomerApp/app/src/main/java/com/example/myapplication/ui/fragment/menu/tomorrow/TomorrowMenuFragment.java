package com.example.myapplication.ui.fragment.menu.tomorrow;

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
import com.example.myapplication.databinding.FragmentTomorrowTabBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.details.ItemDetailFragment;
import com.example.myapplication.util.android.base.BaseFragment;
import com.example.myapplication.util.common.State;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class TomorrowMenuFragment extends BaseFragment<FragmentTomorrowTabBinding> {
    private List<Product> products = new ArrayList<>();
    private TomorrowMenuAdapter adapter;

    private TomorrowMenuViewModel viewModel;

    private TomorrowMenuFragment() {}

    public static TomorrowMenuFragment newInstance() {
        return new TomorrowMenuFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTomorrowTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(TomorrowMenuViewModel.class);

        adapter = new TomorrowMenuAdapter(products);
        binding.recyclerviewVeg.setAdapter(adapter);
        binding.recyclerviewVeg.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewVeg.setHasFixedSize(false);

        adapter.setOnItemClickListener(new TomorrowMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
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
                ItemDetailFragment foodDetailFragment = ItemDetailFragment.newInstance(products.get(index));
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(TomorrowMenuFragment.class.getName())
                        .commit();
            }
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