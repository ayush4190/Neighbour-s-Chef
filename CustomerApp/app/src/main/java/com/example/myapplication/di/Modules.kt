package com.example.myapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.CustomerApp
import com.example.myapplication.model.Cart
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Contains common app-level components like [SharedPreferences]
 */
fun appModule(context: Context): Kodein.Module = Kodein.Module("AppModule") {
    val sharedPreferences = provideSharedPreferences(context)

    bind<SharedPreferences>() with singleton { sharedPreferences }

    bind<Cart>() with singleton { provideCart(sharedPreferences) }

    bind<CustomerApp>() with singleton { context as CustomerApp }
}

/**
 * Contains Room DB-specific components like [androidx.room.Dao] and [androidx.room.RoomDatabase]
 */
fun roomModule(context: Context): Kodein.Module = Kodein.Module("RoomModule") {
    // bind Room database
}