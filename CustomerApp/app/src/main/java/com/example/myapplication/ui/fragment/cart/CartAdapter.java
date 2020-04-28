package com.example.myapplication.ui.fragment.cart;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.CardviewCartBinding;
import com.example.myapplication.ui.fragment.menu.today.TodaysMenuAdapter;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> implements View.OnClickListener{
    private final String TAG = "ADAPTER";
    private int cartQuantity ;
    private TextView mTextView;

    CartAdapter() {}

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(
                CardviewCartBinding.inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position) {
        holder.bindTo(null);
    }

    @Override
    public int getItemCount() {
        return 1;
//                ShoppingCartItem.getInstance(mContext).getFoodTypeSize();
    }

    void deleteData(int position) {

    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    private TodaysMenuAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(TodaysMenuAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,String.valueOf(view.getTag()));
        }
        else{
            Log.e("CLICK", "ERROR");
        }
    }

}

