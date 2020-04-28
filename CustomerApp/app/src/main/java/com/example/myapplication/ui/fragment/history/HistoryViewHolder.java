package com.example.myapplication.ui.fragment.history;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.databinding.CardviewHistoryBinding;
import com.example.myapplication.model.Order;
import com.example.myapplication.util.android.base.BaseViewHolder;

class HistoryViewHolder extends BaseViewHolder<CardviewHistoryBinding, Order> {
    HistoryViewHolder(@NonNull CardviewHistoryBinding binding) {
        super(binding);
    }

    @Override
    public void bindTo(@Nullable Order item) {
//        holder.mTextId.setText("" + orders.get(position).getId());
//        holder.mTextName.setText(orders.get(position).getName());
//        holder.mTextQuantity.setText("" + orders.get(position).getQuantity());
//        holder.mTextTotal.setText("" + orders.get(position).getTotal());
//        holder.mTextDate.setText("" + orders.get(position).getDate());
//        holder.mTextAddress.setText(orders.get(position).getAddress());

        binding.historyTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
//                bundle.putInt("OrderId", orders.get(position).getId());
//                TrackFragment trackFrag = new TrackFragment();
//                trackFrag.setArguments(bundle);
//                ((HomePageActivity) mContext).getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
//                        .replace(R.id.main_fragment_container, trackFrag)
//                        .addToBackStack(AllTabFragment.class.getName())
//                        .commit();
            }
        });

        binding.historyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
