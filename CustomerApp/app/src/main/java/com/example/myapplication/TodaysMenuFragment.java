package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodaysMenuFragment  extends Fragment {

    ArrayList<ItemList> mItemList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TodaysMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    private String baseUrl = "https://neighbour-s-chef.firebaseio.com/Development";


    private String TAG = "ALLFOOD";

    public TodaysMenuFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.todays_menufrag, container, false);

        if (mItemList.size() == 0) {
          
        }


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_all);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TodaysMenuAdapter(getActivity(), mItemList);
        adapter.setOnItemClickListener(new TodaysMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Bundle itemInfo = new Bundle();
                for (int i = 0; i < mItemList.size(); i++) {
                    if (mItemList.get(i).getId() == Integer.valueOf(data)) {
                        itemInfo.putInt("foodId", mItemList.get(i).getId());
                        itemInfo.putString("foodName", mItemList.get(i).getProduct_name());
                        itemInfo.putDouble("foodPrice", mItemList.get(i).getPrice());

                        break;
                    }
                }
                ItemDetailFragment foodDetailFragment = new ItemDetailFragment();
                foodDetailFragment.setArguments(itemInfo);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(TodaysMenuFragment.class.getName())
                        .commit();
            }
        });

        mRecyclerView.setAdapter(adapter);


        return view;
    }
    ///


}