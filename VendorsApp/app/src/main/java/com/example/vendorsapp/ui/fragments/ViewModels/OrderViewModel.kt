package com.example.vendorsapp.ui.fragments.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendorsapp.constants.Node_BASE
import com.example.vendorsapp.constants.Node_ORDER
import com.example.vendorsapp.data.Orders
import com.example.vendorsapp.data.Product
import com.google.firebase.database.*

class OrderViewModel: ViewModel()  {

    private val dbORDER = FirebaseDatabase.getInstance().getReference().child(Node_BASE).child(
        Node_ORDER)
    private val _Orders = MutableLiveData<List<Orders>>()
    val order : LiveData<List<Orders>>
        get() = _Orders


    private val _result = MutableLiveData<Exception?>()
    val result : LiveData<Exception?>
        get() = _result



    private val childEventListener=object :ChildEventListener{
        override fun onCancelled(databaseError: DatabaseError) {
            TODO("Not yet implemented")
        }

        override fun onChildMoved(snapshot: DataSnapshot, p1: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildChanged(snapshot: DataSnapshot, p1: String?) {
            val order = snapshot.getValue(Orders::class.java)

        }

        override fun onChildAdded(snapshot: DataSnapshot, p1: String?) {
            TODO("Not yet implemented")
        }

        override fun onChildRemoved(snapshot: DataSnapshot) {
            TODO("Not yet implemented")
        }

    }
    fun fetchOrders()
    {
        dbORDER.addValueEventListener(object : ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    val orders = mutableListOf<Orders>()
                    for (ordersnapshot in snapshot.children)
                    {
                        Log.v("check", ordersnapshot.getValue(Orders::class.java).toString())
                        val temporders = ordersnapshot.getValue(Orders::class.java)
                       temporders?.let { orders.add(it) }
                    }
                    _Orders.value = orders
                }
            }

        })
    }


    override fun onCleared() {
        super.onCleared()
        dbORDER.removeEventListener(childEventListener)
    }
}