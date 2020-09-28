package com.neighbourschef.customer.ui.fragment.registration

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentPhoneSignInBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.isEmpty
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class PhoneSignInFragment : DialogFragment() {
    private var currentBinding: FragmentPhoneSignInBinding? = null
    private val binding: FragmentPhoneSignInBinding
        get() = currentBinding!!

    private var shouldRequestCode = false
    private var verificationId: String? = null
    private var token: PhoneAuthProvider.ForceResendingToken? = null

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            binding.editCode.setText(credential.smsCode)
            signIn(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            toast(e.message ?: e.toString())
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            beginTimer()
            binding.btnSubmit.text = getString(R.string.verify_otp)
            this@PhoneSignInFragment.verificationId = verificationId
            this@PhoneSignInFragment.token = token
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentPhoneSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            shouldRequestCode = !shouldRequestCode

            if (shouldRequestCode) {
                binding.btnSubmit.text = getString(R.string.submit)
                binding.layoutCode.visibility = View.INVISIBLE

                if (validateInput()) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + binding.editPhone.asString().trim(),
                        60,
                        TimeUnit.SECONDS,
                        requireActivity(),
                        callbacks
                    )
                    binding.layoutCode.visibility = View.VISIBLE
                } else {
                    shouldRequestCode = !shouldRequestCode
                }
            } else {
                binding.btnSubmit.text = getString(R.string.verify_otp)
                if (binding.editCode.isEmpty()) {
                    binding.layoutCode.error = "Must not be empty"
                    binding.layoutCode.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                } else {
                    binding.layoutCode.error = null
                    if (verificationId != null) {
                        signIn(PhoneAuthProvider.getCredential(verificationId!!, binding.editCode.asString()))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }

    private fun signIn(credential: PhoneAuthCredential) {
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = it.result!!.user!!
                    lifecycleScope.launch {
                        FirebaseRepository.getUser(currentUser.uid).collect { res ->
                            when (res) {
                                is Result.Value -> {
                                    if (res.value == User() ||
                                        res.value.address == Address.EMPTY ||
                                        res.value.phoneNumber == ""
                                    ) {
                                        val user = User(
                                            binding.editName.asString().trim(),
                                            currentUser.email ?: "",
                                            binding.editPhone.asString().trim(),
                                            Address.EMPTY
                                        )
                                        FirebaseRepository.saveUser(user, currentUser.uid)
                                        findNavController().navigate(
                                            MobileNavigationDirections.navigateToProfile(),
                                            navOptions {
                                                popUpTo(R.id.mobile_navigation) {
                                                    inclusive = true
                                                }
                                            }
                                        )
                                    } else {
                                        findNavController().navigate(MobileNavigationDirections.navigateToMenu())
                                    }
                                }
                                is Result.Error -> toast(res.error.message ?: res.error.toString())
                            }
                        }
                    }
                } else {
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        toast("Invalid code entered!")
                    } else {
                        toast("Authentication failed: ${it.exception?.message}")
                    }
                }
            }
    }

    private fun validateInput(): Boolean {
        var valid = true

        if (binding.editPhone.isEmpty()) {
            valid = false
            binding.layoutPhone.error = "Required"
            binding.layoutPhone.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        } else {
            binding.layoutPhone.error = null
        }

        if (binding.editName.isEmpty()) {
            valid = false
            binding.layoutName.error = "Required"
            binding.layoutName.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        } else {
            binding.layoutName.error = null
        }

        return valid
    }

    private fun beginTimer() {
        binding.textTimer.visibility = View.VISIBLE
        lifecycleScope.launch {
            var time = 60
            while (time > 0) {
                binding.textTimer.text = String.format("%d seconds", time)
                delay(1000)
                time--
            }

            binding.textTimer.text = SpannableString(getString(R.string.resend_otp)).apply {
                setSpan(UnderlineSpan(), 0, length, 0)
            }
            binding.textTimer.setOnClickListener {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+91" + binding.editPhone.asString().trim(),
                    60,
                    TimeUnit.SECONDS,
                    requireActivity(),
                    callbacks
                )
            }
        }
    }
}
