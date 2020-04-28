package com.example.myapplication.ui.fragment.menu.restoftheweek;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentRestOfTheWeekTabBinding;
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

public class RestoftheWeekFragment extends BaseFragment<FragmentRestOfTheWeekTabBinding> {
    private List<Product> products = new ArrayList<>();
    private RestoftheWeekAdapter adapter;

    public RestoftheWeekFragment() {}

    public static RestoftheWeekFragment newInstance() {
        return new RestoftheWeekFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestOfTheWeekTabBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RestoftheWeekAdapter(products);
       getItemList();
        adapter.clear();
        binding.recyclerviewNonveg.setAdapter(adapter);
        binding.recyclerviewNonveg.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();

        binding.recyclerviewNonveg.setHasFixedSize(false);
        adapter.setOnItemClickListener(new RestoftheWeekAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                int index = 0;
                for (int i = 0; i < products.size(); i++){
                    if (products.get(i).getId().equals(data)){
                        index = i;
//                        itemInfo.putString("foodCat", mItemList.get(i).getCategory());
//                        itemInfo.putString("foodRec", foods.get(i).getRecepiee());
                        //  itemInfo.putString("foodImage", foods.get(i).getImageUrl());
                        break;
                    }
                }
                ItemDetailFragment foodDetailFragment = ItemDetailFragment.newInstance(products.get(index));
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                        .replace(R.id.main_fragment_container, foodDetailFragment)
                        .addToBackStack(RestoftheWeekFragment.class.getName())
                        .commit();
            }
        });

    }


    private void getItemList() {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Development").child("Rest of the week");
        Query q = databaseReference;
        q.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    Product data = dataSnapshot.getValue(Product.class);
                    adapter.addItem(data, dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                    binding.recyclerviewNonveg.setAdapter(adapter);
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