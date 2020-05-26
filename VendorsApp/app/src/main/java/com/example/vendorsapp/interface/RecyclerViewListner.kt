package com.example.vendorsapp.`interface`

import android.view.View
import com.example.vendorsapp.data.Orders
import com.example.vendorsapp.data.Product

interface RecyclerViewListner  {


    fun OnRecyclerViewItemClicked(view: View, orders: Orders)


}