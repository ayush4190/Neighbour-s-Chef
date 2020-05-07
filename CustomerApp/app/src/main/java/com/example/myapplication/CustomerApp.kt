package com.example.myapplication

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.myapplication.di.appModule
import com.example.myapplication.model.Cart
import com.example.myapplication.util.android.HyperlinkedDebugTree
import com.example.myapplication.util.common.JSON
import com.example.myapplication.util.common.PREFERENCE_CART
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.instance
import timber.log.Timber

class CustomerApp: Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        importAll(androidXModule(this@CustomerApp), appModule(this@CustomerApp))
    }
    var account: GoogleSignInAccount? = null
    lateinit var googleSignInClient: GoogleSignInClient

    private val sharedPreferences by kodein.instance<SharedPreferences>()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())

        if (!sharedPreferences.contains(PREFERENCE_CART)) {
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.stringify(Cart.serializer(), Cart.EMPTY))
            }
        }

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)
    }
}