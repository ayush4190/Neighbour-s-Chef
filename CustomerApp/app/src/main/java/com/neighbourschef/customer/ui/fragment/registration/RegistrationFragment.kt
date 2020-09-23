package com.neighbourschef.customer.ui.fragment.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.RC_SIGN_IN
import com.neighbourschef.customer.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@ExperimentalCoroutinesApi
class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>() {
    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        (requireActivity() as MainActivity).googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        currentUser = auth.currentUser
        if (currentUser != null) {
            navController.navigate(MobileNavigationDirections.navigateToMenu())
        }
        binding.btnSignIn.setOnClickListener { signIn() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_SIGN_IN -> {
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    binding.progressBar.isVisible = true
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(requireActivity()) {
                            if (it.isSuccessful) {
                                binding.progressBar.isVisible = false
                                currentUser = auth.currentUser

                                lifecycleScope.launch {
                                    FirebaseRepository.getUser(currentUser!!.uid).collect { res ->
                                        when (res) {
                                            is Result.Value -> {
                                                if (res.value == User() ||
                                                    res.value.address == Address.EMPTY ||
                                                    res.value.phoneNumber == "") {
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
                                                            popUpTo(R.id.mobile_navigation) {
                                                                inclusive = true
                                                            }
                                                        }
                                                    )
                                                } else {
                                                    navController.navigate(MobileNavigationDirections.navigateToMenu())
                                                }
                                            }
                                            is Result.Error -> toast(res.error.message ?: res.error.toString())
                                        }
                                    }
                                }
                            } else {
                                binding.progressBar.isVisible = false
                                toast("Authentication failed: ${it.exception?.message}")
                            }
                        }
                } catch (e: ApiException) {
                    binding.progressBar.isVisible = false
                    Timber.w(e, "signInResult:failed code=${e.statusCode}, message=${e.message}")
                }
            }
        }
    }

    private fun signIn() =
        requireActivity().startActivityFromFragment(
            this,
            (requireActivity() as MainActivity).googleSignInClient.signInIntent,
            RC_SIGN_IN
        )
}
