package com.example.myapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.util.common.PREFERENCE_FILE_KEY
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Contains common app-level components like [SharedPreferences]
 */
fun appModule(context: Context): Kodein.Module = Kodein.Module("AppModule") {
    bind<SharedPreferences>() with singleton {
        context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    }
}

/**
 * Contains Room DB-specific components like [androidx.room.Dao] and [androidx.room.RoomDatabase]
 */
fun roomModule(context: Context): Kodein.Module = Kodein.Module("RoomModule") {
    // bind Room database
}