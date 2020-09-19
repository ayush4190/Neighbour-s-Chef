package com.neighbourschef.customer.ui.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import coil.Coil
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogAccountBinding
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.toast

class SettingsFragment: PreferenceFragmentCompat() {
    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private val currentUser: FirebaseUser? by lazy(LazyThreadSafetyMode.NONE) { auth.currentUser }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) =
        setPreferencesFromResource(R.xml.settings, rootKey)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenToSettings()
    }

    private fun listenToSettings() {
        val accountPreference = preferenceManager.findPreference(
            requireContext().getString(R.string.pref_account)
        ) as? Preference
        accountPreference?.let { pref ->
            if (currentUser != null) {
                pref.isEnabled = true
                pref.title = currentUser!!.displayName

                lifecycleScope.launchWhenCreated {
                    currentUser!!.photoUrl?.let {
                        pref.icon = when (val result = Coil.imageLoader(requireContext())
                            .execute(ImageRequest.Builder(requireContext()).data(it).apply {
                                transformations(
                                    CircleCropTransformation(),
                                    CircleBorderTransformation()
                                )
                            }.build())) {
                            is SuccessResult -> result.drawable
                            is ErrorResult -> {
                                toast(result.throwable.message ?: result.throwable.toString())
                                ContextCompat.getDrawable(requireContext(), R.drawable.ic_person_outline_60)
                            }
                        }
                    }
                }
                pref.setOnPreferenceClickListener {
                    val dialogBinding = DialogAccountBinding.inflate(LayoutInflater.from(requireContext()))
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setView(dialogBinding.root)
                        .show()

                    dialogBinding.imgAccount.apply {
                        load(currentUser!!.photoUrl) {
                            fallback(R.drawable.ic_person_outline_60)
                            transformations(
                                CircleCropTransformation(),
                                CircleBorderTransformation()
                            )
                        }
                    }
                    dialogBinding.textAccountName.text = currentUser!!.displayName
                    dialogBinding.textAccountEmail.visibility = View.VISIBLE
                    dialogBinding.textAccountEmail.text = currentUser!!.email

                    dialogBinding.btnAccountRemove.setOnClickListener {
                        dialog.dismiss()
                        removeAccount()
                        findNavController().navigateUp()
                    }

                    true
                }
            } else {
                pref.isEnabled = false
            }
        }
    }

    private fun removeAccount() {
        currentUser!!.delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    toast("Account removed!")
                }
                findNavController().navigate(MobileNavigationDirections.navigateToRegistration())
            }
            .addOnFailureListener {
                toast("Unable to revoke access. error=${it.message}")
            }
    }
}
