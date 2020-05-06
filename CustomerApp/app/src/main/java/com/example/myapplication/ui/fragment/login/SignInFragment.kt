package com.example.myapplication.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignInBinding
import com.example.myapplication.ui.activity.main.MainActivity
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber
import java.util.*

class SignInFragment private constructor() :
    BaseFragment<FragmentSignInBinding?>() {
    private var googleSignInClient: GoogleSignInClient? = null
    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.btnSignIn.setOnClickListener { v: View? -> signInToGoogle() }
        configureGoogleClient()
    }

    private fun configureGoogleClient() {
        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient =
            GoogleSignIn.getClient(Objects.requireNonNull(requireActivity()), gso)
        // Set the dimensions of the sign-in button.
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = firebaseAuth!!.currentUser
        if (currentUser != null) {
            Timber.d("Currently Signed in: %s", currentUser.email)
            toast(
                Objects.requireNonNull(requireContext()),
                "Currently Logged in: " + currentUser.email
            )
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }
    }

    private fun signInToGoogle() {
        val signInIntent = googleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account =
                    task.getResult(ApiException::class.java)
                toast(
                    Objects.requireNonNull(requireContext()),
                    "Google Sign in Succeeded"
                )
                assert(account != null)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.tag(TAG).w(e, "Google sign in failed")
                toast(
                    Objects.requireNonNull(requireContext()),
                    "Google Sign in Failed $e"
                )
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        Timber.d("firebaseAuthWithGoogle:%s", account!!.id)
        val credential =
            GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(
                Objects.requireNonNull(requireActivity())
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = firebaseAuth!!.currentUser!!
                    Timber.d(
                        "signInWithCredential:success: currentUser: %s",
                        user.email
                    )
                    toast(
                        Objects.requireNonNull(
                            requireContext()
                        ), "Firebase Authentication Succeeded "
                    )
                    launchMainActivity(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.tag(TAG)
                        .w(task.exception, "signInWithCredential:failure")
                    toast(
                        Objects.requireNonNull(
                           requireContext()
                        ), "Firebase Authentication failed:" + task.exception
                    )
                }
            }
    }

    private fun launchMainActivity(user: FirebaseUser?) {
        if (user != null) {
            Objects.requireNonNull(user.displayName)?.let {
                toast(
                    Objects.requireNonNull(requireContext()),
                    it
                )
            }
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 1001
        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}