package com.example.vendorsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class orderlistAdapter extends RecyclerView.Adapter<OrderHolder> implements View.OnClickListener  {



    private Context mContext;
    List<OrderDetail> mItemList;
    private List<String> s;
    public String TAG = "TODAYSMENU";
    public static OrderDetail n;

    public orderlistAdapter(Context mContext, List<OrderDetail> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
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
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }

    }


    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.order_view, parent, false);
        OrderHolder allHolder = new OrderHolder(v);
        v.setOnClickListener(this);
        return allHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {



      holder.mOrderView.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("RestrictedApi")
          @Override
          public void onClick(View v) {
              AppCompatActivity activity = (AppCompatActivity) v.getContext();
             FloatingActionButton floatingActionButton =(FloatingActionButton) activity.findViewById(R.id.fab);
             floatingActionButton.setVisibility(View.GONE);
              Fragment myFragment = new OrderListFargment();
              activity.getSupportFragmentManager().beginTransaction().
                      replace(R.id.main_fragment_container, myFragment).
                      addToBackStack("Complete_order_list").commit();


          }
      });


    }

    @Override
    public int getItemCount() {
        return 1;
    }


}


class  OrderHolder extends RecyclerView.ViewHolder
{
     TextView mOrder_name , mOrder_quantity , mOrder_total , mOrder_id ,mOrder_date , mOrder_address ,mOrderView, mOrderCancel ;
    public OrderHolder(@NonNull View itemView) {
        super(itemView);
        mOrder_id = itemView.findViewById(R.id.order_id);
        mOrder_name = itemView.findViewById(R.id.order_name);
        mOrder_quantity = itemView.findViewById(R.id.order_quantity);
        mOrder_total = itemView.findViewById(R.id.order_total);
        mOrder_address = itemView.findViewById(R.id.order_address);
        mOrder_date = itemView.findViewById(R.id.order_date);
        mOrderView = itemView.findViewById(R.id.order_view);
        mOrderCancel = itemView.findViewById(R.id.order_cancel);







    }

}
