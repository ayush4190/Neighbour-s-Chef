package com.neighbourschef.customer.ui.fragment.registration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentRegistrationBinding
import com.neighbourschef.customer.databinding.NavHeaderMainBinding
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.isProfileSetup
import com.neighbourschef.customer.util.common.RC_SIGN_IN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance
import timber.log.Timber

@ExperimentalCoroutinesApi
class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>(), DIAware {
    override val di by di()
    val app by instance<CustomerApp>()
    val database by instance<CustomerDatabase>()
    val sharedPreferences by instance<SharedPreferences>()

    private var currentNavHeaderMainBinding: NavHeaderMainBinding? = null
    private val navHeaderMainBinding: NavHeaderMainBinding
        get() = currentNavHeaderMainBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        currentNavHeaderMainBinding = NavHeaderMainBinding.bind(
            (requireActivity() as MainActivity).binding.navView.getHeaderView(0)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener { signIn() }

        updateNavHeader()

        /*
            TODO: Request permissions
            user: aayush
            date: 7/5/20
        */
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    app.account = task.result
                    updateNavHeader()

                    if (isProfileSetup(sharedPreferences)) {
                        findNavController().navigate(
                            MobileNavigationDirections.navigateToHome(),
                            navOptions {
                                popUpTo(R.id.nav_registration) {
                                    inclusive = true
                                }
                            }
                        )
                    } else {
                        lifecycleScope.launch(Dispatchers.IO) {
                            database.userDao().insert(User(
                                app.account!!.displayName!!,
                                app.account!!.email!!,
                                "",
                                Address.EMPTY
                            ))
                        }
                        findNavController().navigate(
                            MobileNavigationDirections.navigateToProfile(),
                            navOptions {
                                popUpTo(R.id.nav_registration) {
                                    inclusive = true
                                }
                            }
                        )
                    }
                } catch (e: ApiException) {
                    Timber.w(e, "signInResult:failed code=${e.statusCode}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentNavHeaderMainBinding = null
    }

    private fun updateNavHeader() {
        if (app.account != null) {
            navHeaderMainBinding.textUserName.text = app.account!!.displayName
            navHeaderMainBinding.textUserEmail.text = app.account!!.email
            navHeaderMainBinding.imgUser.load(app.account!!.photoUrl) {
                fallback(R.drawable.ic_action_name)
                transformations(CircleCropTransformation(), CircleBorderTransformation())
            }
            findNavController().navigate(
                MobileNavigationDirections.navigateToHome(),
                navOptions {
                    popUpTo(R.id.nav_registration) {
                        inclusive = true
                    }
                }
            )
        } else {
            navHeaderMainBinding.imgUser.load(R.drawable.ic_action_name)
            navHeaderMainBinding.textUserName.text = getString(R.string.sign_in)
        }
    }

    private fun signIn() =
        requireActivity().startActivityFromFragment(
            this,
            app.googleSignInClient.signInIntent,
            RC_SIGN_IN
        )
}