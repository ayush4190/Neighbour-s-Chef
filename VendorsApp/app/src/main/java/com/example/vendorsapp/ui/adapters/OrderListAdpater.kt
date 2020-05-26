package com.example.vendorsapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vendorsapp.R
import com.example.vendorsapp.`interface`.RecyclerViewListner
import com.example.vendorsapp.data.Orders
import kotlinx.android.synthetic.main.cardview_orderlist.view.*

class OrderListAdpater : RecyclerView.Adapter<OrderListAdpater.OrderViewModel>() {

    private var orders= mutableListOf<Orders>()

    var listner:RecyclerViewListner ?= null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        OrderViewModel (
        LayoutInflater.from(parent.context).inflate(R.layout.cardview_orderlist,parent,false))


    override fun getItemCount()= orders.size



    override fun onBindViewHolder(holder: OrderViewModel, position: Int) {
        holder.view.textview_order_name.text = orders[position].name
        holder.view.textview_order_id.text = orders[position].id
        holder.view.textviewbutton_order_view.setOnClickListener {
           listner?.OnRecyclerViewItemClicked(it,orders[position])

       }
    }

    fun setAuthor(authors : List<Orders>)
    {
        this.orders = authors as MutableList<Orders>
        notifyDataSetChanged()
    }




    class OrderViewModel(val view : View) : RecyclerView.ViewHolder(view)

}