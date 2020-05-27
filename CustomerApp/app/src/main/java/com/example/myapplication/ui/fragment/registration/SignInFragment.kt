package com.example.myapplication.ui.fragment.registration

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignInBinding
import com.example.myapplication.databinding.NavHeaderMainBinding
import com.example.myapplication.ui.activity.MainActivity
import com.example.myapplication.util.android.CircleBorderTransformation
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.log
import com.example.myapplication.util.android.toast
import com.example.myapplication.util.common.RC_SIGN_IN
import com.example.myapplication.util.common.USER_ID
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class SignInFragment: BaseFragment<FragmentSignInBinding>() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private val firebaseAuth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureGoogleClient()
        binding.btnSignIn.setOnClickListener { signInToGoogle() }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val navHeaderMainBinding = NavHeaderMainBinding.bind(
                (requireActivity() as MainActivity).binding.navView.getHeaderView(0)
            )
            navHeaderMainBinding.textUserName.text = currentUser.displayName
            navHeaderMainBinding.textUserEmail.text = currentUser.email
            navHeaderMainBinding.imgUser.load(currentUser.photoUrl) {
                fallback(R.drawable.ic_action_name)
                transformations(CircleCropTransformation(), CircleBorderTransformation())
            }
            "Currently Signed in ${currentUser.email}".log()
            toast(requireContext(), "Currently Signed in ${currentUser.email}")
            findNavController().navigate(
                MobileNavigationDirections.navigateToHome(),
                navOptions {
                    popUpTo(R.id.nav_registration) {
                        inclusive = true
                    }
                }
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.result
                    toast(requireContext(), "Google Sign in Succeeded")
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Timber.w(e, "Google sign in failed")
                    toast(requireContext(), "Google Sign in Failed $e")
                }
            }
        }
    }

    private fun configureGoogleClient() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // for the requestIdToken, this is in the values.xml file that
            // is generated from your google-services.json
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        // Set the dimensions of the sign-in button.
    }

    private fun signInToGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        "firebaseAuthWithGoogle:${account?.id}".log()

        val credential =
            GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth.currentUser
                    "signInWithCredential:success: currentUser: ${user?.email}".log()
                    toast(requireContext(), "Firebase Authentication Succeeded ")
                    launchMainActivity(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.w(it.exception, "signInWithCredential:failure")
                    toast(requireContext() , "Firebase Authentication failed:${it.exception}")
                }
            }
    }

    private fun launchMainActivity(user: FirebaseUser?) {
        if (user != null) {
            toast(requireContext(), user.displayName ?: user.toString())

            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    companion object {
        fun newInstance(): SignInFragment = SignInFragment()
    }
}