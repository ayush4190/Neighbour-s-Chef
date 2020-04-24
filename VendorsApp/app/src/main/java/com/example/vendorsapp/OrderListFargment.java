package com.example.vendorsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.ArrayList;
import java.util.List;

public class OrderListFargment extends Fragment {

    private Context context;
    private RecyclerView mRecyclerView ;
    private OrderItemListAdapter adapter;
    private List<ItemDetail> mItemList = new ArrayList<>();
    public OrderListFargment(Context context, RecyclerView mRecyclerView) {
        this.context = context;
        this.mRecyclerView = mRecyclerView;
    }

    public OrderListFargment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_orderlist);
        adapter = new OrderItemListAdapter(getActivity(), mItemList);
//        getItemList();
        adapter.clear();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();


        mRecyclerView.setHasFixedSize(false);


    }


}
