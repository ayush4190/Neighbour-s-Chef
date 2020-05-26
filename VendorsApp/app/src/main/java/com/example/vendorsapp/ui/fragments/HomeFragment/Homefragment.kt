package com.example.vendorsapp.ui.fragments.HomeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView

import com.example.vendorsapp.R
import com.example.vendorsapp.`interface`.RecyclerViewListner
import com.example.vendorsapp.data.Orders
import com.example.vendorsapp.data.Product
import com.example.vendorsapp.ui.adapters.OrderListAdpater
import com.example.vendorsapp.ui.fragments.BaseFragment
import com.example.vendorsapp.ui.fragments.ViewModels.OrderViewModel
import com.example.vendorsapp.ui.fragments.additems.addItems
import kotlinx.android.synthetic.main.fragment_homefragment.*


class Homefragment : BaseFragment(), RecyclerViewListner{


    private lateinit var viewModel: OrderViewModel
    private val adapter = OrderListAdpater()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        return inflater.inflate(R.layout.fragment_homefragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        adapter.listner=this
        recyclerview_order.adapter=adapter
        viewModel.fetchOrders()

        viewModel.order.observe(viewLifecycleOwner, Observer {
            adapter.setAuthor(it)

        })

        floatinfactionbutton_additem.setOnClickListener {
            val action = HomefragmentDirections.actionAddItem()
            Navigation.findNavController(it).navigate(action)
        }
    }



    override fun OnRecyclerViewItemClicked(view: View, orders: Orders) {

        when(view.id)
        {
            R.id.textviewbutton_order_view ->{
                Toast.makeText(requireActivity(),"keep going",Toast.LENGTH_LONG).show()
            }
        }
    }


}
