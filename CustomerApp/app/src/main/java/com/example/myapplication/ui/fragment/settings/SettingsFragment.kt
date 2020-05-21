package com.example.myapplication.ui.fragment.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import coil.Coil
import coil.api.load
import coil.request.ErrorResult
import coil.request.GetRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.example.myapplication.CustomerApp
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogAccountBinding
import com.example.myapplication.util.android.CircleBorderTransformation
import com.example.myapplication.util.android.restartApp
import com.example.myapplication.util.android.toast
import com.example.myapplication.util.common.PREFERENCE_THEME
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SettingsFragment: PreferenceFragmentCompat(), KodeinAware {
    override val kodein by kodein()
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
                            .execute(GetRequest.Builder(requireContext()).data(it).apply {
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
                toast(requireContext(), "Account removed!")
                app.account = null
                restartApp(requireActivity())
            }
            .addOnFailureListener {
                toast(requireContext(), "Unable to revoke access. error=${it.message}")
            }
    }
}