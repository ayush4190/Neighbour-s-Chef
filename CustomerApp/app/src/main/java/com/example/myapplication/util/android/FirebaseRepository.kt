package com.example.myapplication.util.android

import com.example.myapplication.util.common.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
object FirebaseRepository {
    private val databaseReference: DatabaseReference by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.database.reference.child(PATH_DEV)
    }

    fun getTodaysMenu(): Flow<State> = databaseReference.child(PATH_TODAY).listen()

    fun getTomorrowsMenu(): Flow<State> = databaseReference.child(PATH_TOMORROW).listen()

    fun getRestOfTheWeeksMenu(): Flow<State> = databaseReference.child(PATH_REST_OF_THE_WEEK).listen()
}