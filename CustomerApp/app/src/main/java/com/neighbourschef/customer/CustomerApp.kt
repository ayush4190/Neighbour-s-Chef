package com.neighbourschef.customer

import android.app.Application
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.content.getSystemService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.jakewharton.threetenabp.AndroidThreeTen
import com.neighbourschef.customer.di.appModule
import com.neighbourschef.customer.di.roomModule
import com.neighbourschef.customer.model.Cart
import com.neighbourschef.customer.util.android.HyperlinkedDebugTree
import com.neighbourschef.customer.util.android.isNetworkAvailable
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.JSON
import com.neighbourschef.customer.util.common.PREFERENCE_CART
import com.neighbourschef.customer.util.common.PREFERENCE_THEME
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.instance
import timber.log.Timber

class CustomerApp: Application(), DIAware {
    override val di: DI = DI.lazy {
        importAll(
            androidXModule(this@CustomerApp),
            appModule(this@CustomerApp),
            roomModule(this@CustomerApp)
        )
    }
    var account: GoogleSignInAccount? = null
    lateinit var googleSignInClient: GoogleSignInClient

    private val sharedPreferences by di.instance<SharedPreferences>()

    override fun onCreate() {
        super.onCreate()

        AndroidThreeTen.init(this)
        Timber.plant(HyperlinkedDebugTree())

        if (!sharedPreferences.contains(PREFERENCE_CART)) {
            sharedPreferences.edit {
                putString(PREFERENCE_CART, JSON.encodeToString(Cart.serializer(), Cart.EMPTY))
            }
        }
        AppCompatDelegate.setDefaultNightMode(
            sharedPreferences.getInt(PREFERENCE_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        )

        if ((getSystemService<ConnectivityManager>())?.isNetworkAvailable() == false) {
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_LONG).show()
        }

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        account = GoogleSignIn.getLastSignedInAccount(this)
    }

    fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                account = null
            }
            .addOnFailureListener {
                toast(this, "Unable to sign out. error=${it.message}")
            }
    }
}