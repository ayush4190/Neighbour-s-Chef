package com.example.vendorsapp.ui.fragments.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vendorsapp.constants.Node_BASE
import com.example.vendorsapp.data.Product
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ItemsViewModel : ViewModel() {

    private val dbORDER = FirebaseDatabase.getInstance().getReference().child(Node_BASE)
    private val _Orders = MutableLiveData<Product>()
    val order : LiveData<Product>
    get() = _Orders

    //for storing the exceptions

    private val _result = MutableLiveData<Exception?>()
    val result : LiveData<Exception?>
        get() = _result




    fun _addItem(id: String, path:String,   product: Product)
    {
            product.id = id
            dbORDER.child(path).child(product.id!!).setValue(product).addOnCompleteListener {
                if(it.isSuccessful)
                {
                     _result.value = null
                }else
                {
                    _result.value = it.exception
                }
            }

    }


}
