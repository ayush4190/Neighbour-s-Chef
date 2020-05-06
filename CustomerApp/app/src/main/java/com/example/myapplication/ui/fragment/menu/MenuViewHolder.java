package com.example.myapplication.ui.fragment.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.CardviewFoodBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.details.ItemDetailFragment;
import com.example.myapplication.ui.fragment.menu.restoftheweek.RestoftheWeekFragment;
import com.example.myapplication.util.android.base.BaseViewHolder;

import timber.log.Timber;

public class MenuViewHolder extends BaseViewHolder<CardviewFoodBinding, Product> {
    private final FragmentManager fragmentManager;

    public MenuViewHolder(@NonNull CardviewFoodBinding binding, final FragmentManager fragmentManager) {
        super(binding);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void bindTo(@Nullable Product item) {
        if (item != null) {
            Timber.d(item.toString());
            binding.getRoot().setOnClickListener(v -> {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(
                                R.id.main_fragment_container,
                                ItemDetailFragment.newInstance(item)
                        )
                        .addToBackStack(RestoftheWeekFragment.class.getName())
                        .commit();
            });

            binding.foodId.setText(item.getId().toString());
            binding.foodName.setText(item.getName());
            binding.foodPrice.setText(String.valueOf(item.getPrice()));

//        binding.foodCategory.setText(foods.get(position).getCategory());
//        binding.foodImg.setImageBitmap(foods.get(position).getImage());

            binding.foodImg.setTag(item.getId());
        }
    }
}
