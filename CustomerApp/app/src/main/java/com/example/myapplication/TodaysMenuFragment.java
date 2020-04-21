package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TodaysMenuFragment  extends Fragment {

    ArrayList<ItemList>  mItemList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TodaysMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;







    public TodaysMenuFragment() {
    }




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.todays_menufrag, container, false);

        // Request Data From Web Service
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
//                for (int i=0; i<foods.size(); i++){
//                    if (foods.get(i).getId() == Integer.valueOf(data)){
//                        itemInfo.putInt("foodId", foods.get(i).getId());
//                        itemInfo.putString("foodName", foods.get(i).getName());
//                        itemInfo.putString("foodCat", foods.get(i).getCategory());
//                        itemInfo.putString("foodRec", foods.get(i).getRecepiee());
//                        itemInfo.putDouble("foodPrice", foods.get(i).getPrice());
//                        itemInfo.putString("foodImage", foods.get(i).getImageUrl());
//                        break;
//                    }
//                }
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


    public void  item_list()
    {
        adapter.clear();
        mRecyclerView.setAdapter(adapter);
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance ().getReference ().child ("Development").child("1");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists ()) {
                   ItemList data = dataSnapshot.getValue (ItemList.class);
                    adapter.addItem (data, dataSnapshot.getKey ());
                    adapter.notifyDataSetChanged ();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
