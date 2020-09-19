package com.neighbourschef.customer.ui.fragment.registration

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentRegistrationBinding
import com.neighbourschef.customer.databinding.NavHeaderMainBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.isProfileSetup
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.RC_SIGN_IN
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import timber.log.Timber

@ExperimentalCoroutinesApi
class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>() {
    val sharedPreferences: SharedPreferences by inject()

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private lateinit var googleSignInClient: GoogleSignInClient
    private var currentUser: FirebaseUser? = null

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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        currentUser = auth.currentUser
        if (currentUser != null) {
            updateNavHeader()
            navController.navigate(MobileNavigationDirections.navigateToHome())
        }
        binding.btnSignIn.setOnClickListener { signIn() }

        updateNavHeader()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(requireActivity()) {
                            if (it.isSuccessful) {
                                currentUser = auth.currentUser
                                if (isProfileSetup(sharedPreferences)) {
                                    navController.navigate(MobileNavigationDirections.navigateToHome())
                                } else {
                                    val user = User(
                                        currentUser!!.displayName!!,
                                        currentUser!!.email!!,
                                        "",
                                        Address.EMPTY
                                    )
                                    FirebaseRepository.saveUser(user, currentUser!!.uid)
                                    navController.navigate(
                                        MobileNavigationDirections.navigateToProfile(),
                                        navOptions {
                                            popUpTo(R.id.nav_registration) {
                                                inclusive = true
                                            }
                                        }
                                    )
                                }
                            } else {
                                toast("Authentication failed: ${it.exception?.message}")
                            }
                            updateNavHeader()
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
        if (currentUser != null) {
            navHeaderMainBinding.textUserName.text = currentUser!!.displayName
            navHeaderMainBinding.textUserEmail.text = currentUser!!.email
            navHeaderMainBinding.imgUser.load(currentUser!!.photoUrl) {
                fallback(R.drawable.ic_person_outline_60)
                transformations(CircleCropTransformation(), CircleBorderTransformation())
            }
            findNavController().navigate(MobileNavigationDirections.navigateToHome())
        } else {
            navHeaderMainBinding.imgUser.load(R.drawable.ic_person_outline_60)
            navHeaderMainBinding.textUserName.text = getString(R.string.sign_in)
        }
    }

    private fun signIn() =
        requireActivity().startActivityFromFragment(
            this,
            googleSignInClient.signInIntent,
            RC_SIGN_IN
        )
}
