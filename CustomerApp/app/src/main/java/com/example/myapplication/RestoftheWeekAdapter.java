package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class RestoftheWeekAdapter extends RecyclerView.Adapter<RestWeekHolder> implements View.OnClickListener {

    private Context mContext;
    List<ItemList> mItemList;
    private List<String> s;
    public String TAG = "TODAYSMENU";
    public static ItemList n;


    public RestoftheWeekAdapter(Context mContext, List<ItemList> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public RestWeekHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
       RestWeekHolder allHolder = new RestWeekHolder(v);
        v.setOnClickListener(this);
        return allHolder;
    }



    @Override
    public void onBindViewHolder(RestWeekHolder holder, int position) {
        n = mItemList.get(position);
        Log.v("alpha", n.getProduct_name());
        holder.mTextId.setText(String.valueOf(n.getProduct_id()));
        holder.mTextName.setText(n.getProduct_name());
        holder.mTextPrice.setText(String.valueOf(n.getProduct_price()));
//        holder.mTextCategory.setText(foods.get(position).getCategory());
//        holder.mImageView.setImageBitmap(foods.get(position).getImage());

        holder.itemView.setTag(mItemList.get(position).getProduct_id());
    }

    public void addItem(ItemList eventsList, String t) {
//        this.s.add(t);
        this.mItemList.add(eventsList);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
//        foods.size();
    }

    public void clear() {
        mItemList.clear();
        notifyDataSetChanged();
    }


    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View view) {


        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view, String.valueOf(view.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }

    }


}


class RestWeekHolder extends RecyclerView.ViewHolder {
    //        NetworkImageView mImage;
    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice;

    public RestWeekHolder(View itemView) {
        super(itemView);
        // mImage = (NetworkImageView) itemView.findViewById(R.id.food_img);
        //  mImageView = (ImageView) itemView.findViewById(R.id.food_img);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextName = (TextView) itemView.findViewById(R.id.food_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_price);
        //    mTextId = (TextView) itemView.findViewById(R.id.food_id);
        //  mTextCategory = (TextView) itemView.findViewById(R.id.food_category);
    }


}