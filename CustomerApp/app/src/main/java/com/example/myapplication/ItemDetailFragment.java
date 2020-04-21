package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ItemDetailFragment extends Fragment {
    TextView mTextId, mTextRecipe, mTextCategory, mTextPrice;

    Button mButtonAdd;
    ImageView mImageView;
    ItemList mItemlist;
    final private String TAG = "FoodDetail";
    CollapsingToolbarLayout collapsingToolbarLayout;


    View view;

    public ItemDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_item_detail, container, false);

       initView();
//        initFoodInfo();
//
        setButtonListener();


        return view;
    }


    private void initView(){
        collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.food_detail_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Food Name");
        mTextId = (TextView) view.findViewById(R.id.food_detail_id);
        mTextRecipe = (TextView) view.findViewById(R.id.food_detail_recipe);
        mTextCategory = (TextView) view.findViewById(R.id.food_detail_category);
        mTextPrice = (TextView) view.findViewById(R.id.food_detail_price);
        mButtonAdd = (Button) view.findViewById(R.id.food_detail_add);
        mImageView = (ImageView) view.findViewById(R.id.food_detail_image);
    }

    private void setButtonListener(){
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  ShoppingCartItem.getInstance(getContext()).addToCart(food);
                TextView cartNumber = (TextView)getActivity().findViewById(R.id.cart_item_number);
             //   cartNumber.setText(String.valueOf(ShoppingCartItem.getInstance(getContext()).getSize()));

                new AlertDialog.Builder(getActivity()).setTitle("Successful!").setIcon(
                        android.R.drawable.ic_dialog_info)
                        .setMessage("Add 1 " + "mItemlist.getProduct_name()" + " to cart!")
                        .setPositiveButton("Jump to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent cartAct = new Intent(getActivity(), CartActivity.class);
                                startActivity(cartAct);
                            }
                        })
                        .setNegativeButton("Continue", null).show();
            }
        });
    }
}