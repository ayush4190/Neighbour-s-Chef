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

import java.util.ArrayList;

public class TomorrowMenuAdapter extends RecyclerView.Adapter<TomorrowHolder> implements View.OnClickListener{

    private Context mContext;

    private ArrayList<ItemList> mItemList;

    public String TAG = "TOMORROWSMENU";

    public TomorrowMenuAdapter(Context mContext, ArrayList<ItemList> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public TomorrowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
        TomorrowHolder tomorrowHolder = new TomorrowHolder(v);
        v.setOnClickListener(this);
        return tomorrowHolder;
    }

    @Override
    public void onBindViewHolder(TomorrowHolder holder, int position) {
        holder.mTextCategory.setVisibility(View.INVISIBLE);
        holder.mTextCateTitle.setVisibility(View.INVISIBLE);

//        holder.mTextId.setText(String.valueOf(foods.get(position).getId()));
//        holder.mTextName.setText(foods.get(position).getName());
//        holder.mTextPrice.setText(String.valueOf(foods.get(position).getPrice()));
//        holder.mImageView.setImageBitmap(foods.get(position).getImage());
//
//        holder.itemView.setTag(foods.get(position).getId());
    }


    @Override
    public int getItemCount()
    {
        return 100 ;
//        foods.size();
    }

    public void notifyData(ArrayList<ItemList> itemLists) {
//        Log.d("notifyData ", foods.size() + "");
        this.mItemList = itemLists;
        //notifyDataSetChanged();
    }

    public void clear() {
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    private TodaysMenuAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(TodaysMenuAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(view,String.valueOf(view.getTag()));
        }
        else{
            Log.e("CLICK", "ERROR");
        }
    }
}



class TomorrowHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice, mTextCateTitle;

    public TomorrowHolder(@NonNull View itemView) {
        super(itemView);


    // mImage = (NetworkImageView) itemView.findViewById(R.id.food_img);
        mImageView = (ImageView) itemView.findViewById(R.id.food_img);
        mTextId = (TextView) itemView.findViewById(R.id.food_id);
        mTextName = (TextView) itemView.findViewById(R.id.food_name);
        mTextPrice = (TextView) itemView.findViewById(R.id.food_price);
        mTextCategory = (TextView) itemView.findViewById(R.id.food_category);
        mTextCateTitle = (TextView) itemView.findViewById(R.id.food_category_title);
    }


}

