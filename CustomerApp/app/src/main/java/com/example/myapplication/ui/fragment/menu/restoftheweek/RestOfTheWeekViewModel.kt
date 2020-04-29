package com.example.myapplication.ui.fragment.menu.restoftheweek

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Product
import com.example.myapplication.util.android.log
import com.example.myapplication.util.common.PATH_DEV
import com.example.myapplication.util.common.PATH_REST_OF_THE_WEEK
import com.example.myapplication.util.common.State
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class RestOfTheWeekViewModel: ViewModel() {
    private val _product: MutableLiveData<State> = MutableLiveData()

    /*
        TODO: Fix state handling
        user: aayush
        date: 29/4/20
    */
    val product: LiveData<State>
        get() {
            if (_product.value == null) {
                FirebaseDatabase.getInstance().reference.child(PATH_DEV).child(PATH_REST_OF_THE_WEEK)
                    .addChildEventListener(object: ChildEventListener {
                        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                            _product.postValue(State.Loading)
                            if (dataSnapshot.exists()) {
                                dataSnapshot.key.log()
                                _product.postValue(
                                    State.Success(dataSnapshot.key!! to dataSnapshot.getValue(
                                        Product::class.java)!!))
                            } else {
                                _product.postValue(State.Failure(s.toString()))
                            }
                        }

                        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
            }
            return _product
        }
}