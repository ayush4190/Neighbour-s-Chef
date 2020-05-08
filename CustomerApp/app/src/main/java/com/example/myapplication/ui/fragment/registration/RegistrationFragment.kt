package com.example.myapplication.ui.fragment.registration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.CustomerApp
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.example.myapplication.databinding.NavHeaderMainBinding
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.util.android.CircleBorderTransformation
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.isProfileSetup
import com.example.myapplication.util.common.RC_SIGN_IN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import timber.log.Timber

@ExperimentalCoroutinesApi
class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>(), KodeinAware {
    override val kodein by kodein()
    val app by instance<CustomerApp>()
    val sharedPreferences by instance<SharedPreferences>()

    private var _navHeaderMainBinding: NavHeaderMainBinding? = null
    private val navHeaderMainBinding: NavHeaderMainBinding
        get() = _navHeaderMainBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        _navHeaderMainBinding = NavHeaderMainBinding.bind(
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

//        val adapter = SectionsPagerAdapter(this)
//        binding.container.adapter = adapter
//        TabLayoutMediator(binding.tabs, binding.container) { tab, position ->
//            val titles = arrayOf("Sign In", "Sign Up")
//            tab.text = titles[position]
//        }.attach()
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
        _navHeaderMainBinding = null
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