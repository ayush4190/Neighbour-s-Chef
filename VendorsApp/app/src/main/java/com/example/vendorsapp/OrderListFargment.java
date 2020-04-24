package com.example.vendorsapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class OrderListFargment extends Fragment {

    private Context context;
    private RecyclerView mRecyclerView ;
    private OrderItemListAdapter adapter;
    private FloatingActionButton floatingActionButton ;
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

            floatingActionButton = (FloatingActionButton) view.findViewById(R.id.Procced);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"Order Proccesing Completed ",Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(),"Check the History section to see processed requests",Toast.LENGTH_SHORT).show();


                    getActivity().getSupportFragmentManager().popBackStackImmediate (null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    FloatingActionButton floatingActionButton =(FloatingActionButton)activity.findViewById(R.id.fab);
                    floatingActionButton.setVisibility(View.VISIBLE);


                }
            });
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
