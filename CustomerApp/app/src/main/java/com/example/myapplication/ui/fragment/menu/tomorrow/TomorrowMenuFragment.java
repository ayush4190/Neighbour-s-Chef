package com.example.myapplication.ui.fragment.menu.tomorrow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.details.ItemDetailFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class TomorrowMenuFragment extends Fragment {

    private List<Product> mProduct = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TomorrowMenuAdapter adapter;


    public TomorrowMenuFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tomorrow_tab, container, false);
    }


    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_veg);
        adapter = new TomorrowMenuAdapter(getActivity(), mProduct);
        getItemList();
        adapter.clear();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();


        mRecyclerView.setHasFixedSize(false);
        adapter.setOnItemClickListener(new TomorrowMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Bundle itemInfo = new Bundle();
                for (int i = 0; i< mProduct.size(); i++){
                    if (mProduct.get(i).getId() == data){
                        itemInfo.putString("foodId", mProduct.get(i).getId());
                        itemInfo.putString("foodName", mProduct.get(i).getName());
//                        itemInfo.putString("foodCat", mItemList.get(i).getCategory());
//                        itemInfo.putString("foodRec", foods.get(i).getRecepiee());
                        itemInfo.putString("foodPrice", mProduct.get(i).getPrice());
                        //  itemInfo.putString("foodImage", foods.get(i).getImageUrl());
                        break;
                    }
                }
                ItemDetailFragment foodDetailFragment = new ItemDetailFragment();
                foodDetailFragment.setArguments(itemInfo);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(TomorrowMenuFragment.class.getName())
                        .commit();
            }
        });

    }


    private void getItemList() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Development").child("Tomorrows menu");
        Query q = databaseReference;
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Product data = dataSnapshot.getValue(Product.class);
                    adapter.addItem(data, dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(adapter);
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