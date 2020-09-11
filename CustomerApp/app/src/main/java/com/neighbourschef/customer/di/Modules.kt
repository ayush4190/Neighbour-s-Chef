package com.neighbourschef.customer.di

import android.content.Context
import android.content.SharedPreferences
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.model.Cart
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

/**
 * Contains common app-level components like [SharedPreferences]
 */
fun appModule(context: Context) = DI.Module("AppModule") {
    val sharedPreferences = provideSharedPreferences(context)

    bind<SharedPreferences>() with singleton { sharedPreferences }

    bind<Cart>() with singleton { provideCart(sharedPreferences) }

    bind<CustomerApp>() with singleton { context as CustomerApp }
}
