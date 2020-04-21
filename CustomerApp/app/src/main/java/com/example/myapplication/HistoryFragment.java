package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;

    private ArrayList<Order> orders = new ArrayList<>();

    private String TAG = "ORDER_HISTORY";

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("History");
    }

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        if (orders.size()==0){

        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_history);
        adapter = new HistoryAdapter(getContext(), orders);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }





}

