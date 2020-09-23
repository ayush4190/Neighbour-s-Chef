package com.neighbourschef.vendor.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentLoginBinding
import com.neighbourschef.vendor.util.android.asString
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.closeKeyboard
import com.neighbourschef.vendor.util.android.isEmpty
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.EMAIL_VENDOR

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private val user by lazy(LazyThreadSafetyMode.NONE) { auth.currentUser }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (user != null) {
            navController.navigate(MobileNavigationDirections.navigateToOrders())
        }
        binding.btnSignIn.setOnClickListener {
            closeKeyboard()
            val res = validateInput()
            if (res != null) {
                binding.progressBar.isVisible = true
                auth.signInWithEmailAndPassword(res.first, res.second)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            toast { "Logged in!" }
                            navController.navigate(MobileNavigationDirections.navigateToOrders())
                        } else {
                            binding.progressBar.isVisible = false
                            toast { it.exception?.message ?: it.exception.toString() }
                        }
                    }
                    .addOnFailureListener {
                        binding.progressBar.isVisible = false
                        toast { it.message ?: it.toString() }
                    }
            }
        }
    }

    private fun validateInput(): Pair<String, String>? {
        var valid = true

        binding.layoutEmail.error = if (binding.editEmail.asString().trim() == EMAIL_VENDOR) {
            null
        } else {
            valid = false
            binding.layoutEmail.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Use the provided vendor email id"
        }

        binding.layoutPassword.error = if (!binding.editPassword.isEmpty()) {
            null
        } else {
            valid = false
            binding.layoutPassword.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Enter the password"
        }

        return if (valid) binding.editEmail.asString().trim() to binding.editPassword.asString().trim() else null
    }
}
