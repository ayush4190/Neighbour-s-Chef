package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartHolder> implements View.OnClickListener{
    private Context mContext;
    private final String TAG = "ADAPTER";
    private int cartQuantity ;
    private TextView mTextView;

    public CartAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_cart, parent,false);
        CartHolder cartHolder = new CartHolder(view);
        return cartHolder;
    }

    @Override
    public void onBindViewHolder(final CartHolder holder, final int position) {



        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"decrement",Toast.LENGTH_SHORT).show();

            }
        });

        holder.btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"increment",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void deleteData(int position) {

    }

    @Override
    public int getItemCount() {

        return 1;
//                ShoppingCartItem.getInstance(mContext).getFoodTypeSize();
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

class CartHolder extends RecyclerView.ViewHolder {
    TextView mTextName, mTextCategory, mTextPrice;
    ImageView mImage;
    TextView mEditQuantity;
    Button btn_minus;
    Button btn_plus;
    TextView total_price;
    public CartHolder(View itemView) {
        super(itemView);
        mTextName = (TextView) itemView.findViewById(R.id.cart_name);
        mTextCategory = (TextView) itemView.findViewById(R.id.cart_category);
        mTextPrice = (TextView) itemView.findViewById(R.id.cart_price);
        mEditQuantity = (TextView) itemView.findViewById(R.id.cart_quantity);
        mImage = (ImageView) itemView.findViewById(R.id.cart_image);


        btn_minus = (Button) itemView.findViewById(R.id.cart_btn_minus);
        btn_plus = (Button) itemView.findViewById(R.id.cart_btn_plus);



    }
}

