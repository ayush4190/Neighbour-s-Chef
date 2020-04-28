package com.example.myapplication.ui.fragment.menu.today;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.TodaysMenufragBinding;
import com.example.myapplication.model.Product;
import com.example.myapplication.ui.fragment.details.ItemDetailFragment;
import com.example.myapplication.util.android.base.BaseFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class TodaysMenuFragment extends BaseFragment<TodaysMenufragBinding> {
    private List<Product> products = new ArrayList<>();
    private TodaysMenuAdapter adapter;

    public TodaysMenuFragment() {}

    public static TodaysMenuFragment newInstance() {
        return new TodaysMenuFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TodaysMenufragBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new TodaysMenuAdapter(products);
        getItemList();
        adapter.clear();
        binding.recyclerviewAll.setAdapter(adapter);
        binding.recyclerviewAll.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        binding.recyclerviewAll.setHasFixedSize(false);
        adapter.setOnItemClickListener(new TodaysMenuAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int index = 0;
                for (int i = 0; i < products.size(); i++){
                    if (products.get(i).getId().equals(data)){
                        index = i;
                        break;
                    }
                }
                ItemDetailFragment foodDetailFragment = ItemDetailFragment.newInstance(products.get(index));
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(TodaysMenuFragment.class.getName())
                        .commit();
            }
        });

    }


    private void getItemList() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Development").child("Today's menu");
        Query q = databaseReference;
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    Log.v("ds_id", dataSnapshot.getKey());
                        Product data = dataSnapshot.getValue(Product.class);
                        adapter.addItem(data, dataSnapshot.getKey());
                        adapter.notifyDataSetChanged();
                        binding.recyclerviewAll.setAdapter(adapter);

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