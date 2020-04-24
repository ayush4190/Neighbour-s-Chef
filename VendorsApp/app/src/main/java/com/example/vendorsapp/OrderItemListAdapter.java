package com.example.vendorsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderItemListAdapter  extends RecyclerView.Adapter<OrderItemListHolder> implements View.OnClickListener  {

    private Context mContext;
    List<ItemDetail> mItemList;
    private List<String> s;
    public String TAG = "TODAYSMENU";
    public static OrderDetail n;


    public OrderItemListAdapter(Context mContext, List<ItemDetail> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }


    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private orderlistAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(orderlistAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }
    }

    @NonNull
    @Override
    public OrderItemListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_details, parent, false);
       OrderItemListHolder allHolder = new OrderItemListHolder(v);
        v.setOnClickListener(this);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemListHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}


class OrderItemListHolder extends RecyclerView.ViewHolder
{

    public OrderItemListHolder(@NonNull View itemView) {
        super(itemView);
    }
}