package com.example.vendorsapp.ui.fragments.additems

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vendorsapp.R
import com.example.vendorsapp.data.Product
import com.example.vendorsapp.ui.fragments.BaseFragment
import com.example.vendorsapp.ui.fragments.ViewModels.ItemsViewModel
import kotlinx.android.synthetic.main.add_items_fragment.*
import java.util.*

class addItems : BaseFragment() {



    var menuDate: String?=null
    private val items =
        arrayOf("Select", "Today's menu", "Tomorrows menu", "Rest of the week")

    private lateinit var viewModel: ItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_items_fragment, container, false)





    }

    @SuppressLint("StringFormatInvalid")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemsViewModel::class.java)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val msg = if(it == null)
            {
                getString(R.string.success)
            }else
            {
                getString(R.string.error,it.message)
            }


        })

        materialbutton_additem.setOnClickListener{
           val name = edittext_item_name.text.toString()
            val price =edittext_price.text.toString()
            val quantity = edittext_quantity.text.toString()
            val foodid = UUID.randomUUID().toString()

            Food_Id.setText(foodid)



            if(name.isEmpty())
            {
                text_input_itemname.error = getString(R.string.name_required)
                return@setOnClickListener
            }
            if(price.isEmpty())
            {
                text_input_price.error = getString(R.string.price_required)
                return@setOnClickListener
            }

            if(quantity.isEmpty())
            {
                text_input_quantity.error = getString(R.string.quantity_required)
                return@setOnClickListener
            }

            val product =Product()
            product.name = name
            product.price = price
            product.quantity = quantity

            viewModel._addItem(foodid,menuDate!!,product)

        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_expandable_list_item_1,items)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :




            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               menuDate = items[position]
            }

        }
    }









}
