package com.example.vendorsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomepageFrag extends Fragment {

    private List<OrderDetail> mOrderList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private orderlistAdapter adapter ;

    public HomepageFrag(List<OrderDetail> mOrderList, RecyclerView mRecyclerView) {
        this.mOrderList = mOrderList;
        this.mRecyclerView = mRecyclerView;
    }

    public HomepageFrag() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }


    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_order);
        adapter = new orderlistAdapter(getActivity(), mOrderList);
//        getItemList();
        adapter.clear();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        mRecyclerView.setHasFixedSize(false);


    }
}
