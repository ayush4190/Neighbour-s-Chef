package com.neighbourschef.vendor.ui.fragment.additems

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.neighbourschef.vendor.databinding.FragmentAddItemsBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.util.android.base.BaseFragment

class AddItemsFragment : BaseFragment<FragmentAddItemsBinding?>() {
    private val product = Product()
    private var count = 4 //number of items that need to be added
    var status = false
    private var foodId: String? = null
    private var menuDate: String? = null

    //create a list of items for the spinner.
    private val items = arrayOf("Select", "Today's menu", "Tomorrows menu", "Rest of the week")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentAddItemsBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        binding!!.addItemButton.setOnClickListener {
            if (TextUtils.isEmpty(binding!!.itemName.text.toString())) {
                binding!!.itemName.error = "Field cannot be left blank"
                count--
            }
            if (TextUtils.isEmpty(binding!!.price.text.toString())) {
                binding!!.price.error = "Field cannot be left blank"
                count--
            }
            if (TextUtils.isEmpty(binding!!.packetQuantity.text.toString())) {
                binding!!.packetQuantity.error = "Field cannot be left blank"
                count--
            }
            if (count == 4) {
                assignValues()
            }
        }
    }

    private fun init() {
        val adapter: ArrayAdapter<String> = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items) {
            override fun isEnabled(position: Int): Boolean = position != 0

            override fun getDropDownView(
                position: Int,
                convertView: View,
                parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textview = view as TextView
                if (position == 0) {
                    textview.setTextColor(Color.GREEN)
                } else {
                    textview.setTextColor(Color.BLACK)
                }
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinner.adapter = adapter
        binding!!.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                menuDate = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        foodId = autoIncrement()
        binding!!.FoodId.setText(foodId)
    }

    private fun assignValues() {
        product.name = binding!!.itemName.text.toString()
        product.name = binding!!.price.text.toString()
        product.quantity = binding!!.packetQuantity.text.toString()
        product.id = foodId!!
        val databaseReference = FirebaseDatabase.getInstance().reference.child("Development").child(
            menuDate!!
        ).child(foodId!!)
        databaseReference.setValue(product)
        databaseReference.setValue(product).addOnCompleteListener {
            Toast.makeText(activity, "item added Succefully", Toast.LENGTH_SHORT).show()
            cleartext()
        }
    }

    private fun cleartext() {
        binding!!.FoodId.setText("")
        binding!!.price.setText("")
        binding!!.itemName.setText("")
        binding!!.packetQuantity.setText("")
    }

    private fun autoIncrement(): String {
        Companion.id++
        return Companion.id.toString()
    }

    companion object {
        private var id = 1 // how to create autoincrement and checks on number of fields
        fun newInstance(): AddItemsFragment = AddItemsFragment()
    }
}
