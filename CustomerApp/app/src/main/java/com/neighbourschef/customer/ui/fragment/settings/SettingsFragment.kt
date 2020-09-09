package com.neighbourschef.customer.ui.fragment.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import coil.Coil
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogAccountBinding
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.PREFERENCE_THEME
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class SettingsFragment: PreferenceFragmentCompat(), DIAware {
    override val di by di()
    val sharedPreferences by instance<SharedPreferences>()
    val app by instance<CustomerApp>()

    private val settingsViewModel by viewModels<SettingsViewModel> { SettingsViewModelFactory(sharedPreferences) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) =
        setPreferencesFromResource(R.xml.settings, rootKey)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenToSettings()
        observeChanges()
    }

    private fun listenToSettings() {
        val accountPreference = preferenceManager.findPreference(requireContext().getString(R.string.pref_account)) as? Preference
        accountPreference?.let { pref ->
            val account = app.account
            if (account != null) {
                pref.isEnabled = true
                pref.title = account.displayName

                lifecycleScope.launchWhenCreated {
                    account.photoUrl?.let {
                        pref.icon = when (val result = Coil.imageLoader(requireContext())
                            .execute(ImageRequest.Builder(requireContext()).data(it).apply {
                                transformations(
                                    CircleCropTransformation(),
                                    CircleBorderTransformation()
                                )
                            }.build())) {
                            is SuccessResult -> result.drawable
                            is ErrorResult -> throw result.throwable
                        }
                    }
                }
                pref.setOnPreferenceClickListener {
                    val dialogBinding = DialogAccountBinding.inflate(LayoutInflater.from(requireContext()))
                    val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setView(dialogBinding.root)
                        .show()

                    dialogBinding.imgAccount.apply {
                        load(account.photoUrl) {
                            fallback(R.drawable.ic_person_outline_60dp)
                            transformations(
                                CircleCropTransformation(),
                                CircleBorderTransformation()
                            )
                        }
                    }
                    dialogBinding.textAccountName.text = account.displayName
                    dialogBinding.textAccountEmail.visibility = View.VISIBLE
                    dialogBinding.textAccountEmail.text = account.email

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

        val themePreference = preferenceManager.findPreference(requireContext().getString(R.string.pref_theme)) as? ListPreference
        themePreference?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                settingsViewModel.theme.value = (newValue as String).toInt()
                true
            }
        }
    }

    private fun observeChanges() {
        settingsViewModel.theme.observe(viewLifecycleOwner) {
            AppCompatDelegate.setDefaultNightMode(it)
            sharedPreferences.edit {
                putInt(PREFERENCE_THEME, it)
            }
        }
    }

    private fun removeAccount() {
        app.googleSignInClient.revokeAccess()
            .addOnCompleteListener {
                toast("Account removed!")
                app.account = null
                restartApp(requireActivity())
            }
            .addOnFailureListener {
                toast("Unable to revoke access. error=${it.message}")
            }
    }
}
