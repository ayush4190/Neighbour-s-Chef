package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TomorrowMenuFragment extends Fragment {


    ArrayList<ItemList> foods = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private TomorrowMenuAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



    public TomorrowMenuFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Request Data From Web Service
        if (foods.size() == 0){
//            objRequestMethod();
        }

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tomorrow_tab, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_veg);
        adapter = new TomorrowMenuAdapter(getContext(), foods);
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
//                    }
//                }
//                FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
//                foodDetailFragment.setArguments(itemInfo);
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
//                        .replace(R.id.main_fragment_container, foodDetailFragment)
//                        .addToBackStack(AllTabFragment.class.getName())
//                        .commit();
          }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }


    public void item_list()
    {
        adapter.clear();

    }

}
