package com.example.myapplication.ui.fragment.menu.tomorrow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Product;

import java.util.List;

public class TomorrowMenuAdapter extends RecyclerView.Adapter<TomorrowHolder> implements View.OnClickListener {

    private Context mContext;
    List<Product> mProduct;
    private List<String> s;
    public String TAG = "TODAYSMENU";
    public static Product n;


    public TomorrowMenuAdapter(Context mContext, List<Product> mProduct) {
        this.mContext = mContext;
        this.mProduct = mProduct;
    }

    @Override
    public TomorrowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.cardview_food, parent, false);
         TomorrowHolder allHolder = new TomorrowHolder(v);
        v.setOnClickListener(this);
        return allHolder;
    }



    @Override
    public void onBindViewHolder(TomorrowHolder holder, int position) {
        n = mProduct.get(position);
        Log.v("alpha", n.getName());
        holder.mTextId.setText(String.valueOf(n.getId()));
        holder.mTextName.setText(n.getName());
        holder.mTextPrice.setText(String.valueOf(n.getPrice()));
//        holder.mTextCategory.setText(foods.get(position).getCategory());
//        holder.mImageView.setImageBitmap(foods.get(position).getImage());

        holder.itemView.setTag(mProduct.get(position).getId());
    }

    public void addItem(Product eventsList, String t) {
//        this.s.add(t);
        this.mProduct.add(eventsList);
    }

    @Override
    public int getItemCount() {
        return mProduct.size();
//        foods.size();
    }

    public void clear() {
        mProduct.clear();
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


class TomorrowHolder extends RecyclerView.ViewHolder {
    //        NetworkImageView mImage;
    ImageView mImageView;
    TextView mTextId, mTextName, mTextCategory, mTextPrice;

    public TomorrowHolder(View itemView) {
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