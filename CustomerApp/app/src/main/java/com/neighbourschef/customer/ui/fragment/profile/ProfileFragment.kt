package com.neighbourschef.customer.ui.fragment.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogSetPhoneBinding
import com.neighbourschef.customer.databinding.FragmentProfileBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.init
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.rotate
import com.neighbourschef.customer.util.android.showIn
import com.neighbourschef.customer.util.android.showOut
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.EXTRA_FIREBASE_UID
import com.neighbourschef.customer.util.common.EXTRA_USER
import com.neighbourschef.customer.util.common.PREFERENCE_PROFILE_SET_UP
import com.neighbourschef.customer.util.common.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    val sharedPreferences: SharedPreferences by inject()

    private val auth: FirebaseAuth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }
    private val currentUser: FirebaseUser by lazy(LazyThreadSafetyMode.NONE) { auth.currentUser!! }
    private val viewModel: ProfileViewModel by viewModels { ProfileViewModelFactory(currentUser.uid) }

    private lateinit var user: User
    private var shouldRotate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAddress.init()
        binding.fabPhone.init()

        setupViews()
        setupListeners()

        lifecycleScope.launch {
            viewModel.user.collectLatest {
                binding.progressBar.isVisible = true
                when (it) {
                    is Result.Value -> {
                        binding.progressBar.isVisible = false
                        user = it.value

                        binding.textUserPhone.text = if (user.phoneNumber == "") {
                            getString(R.string.empty_phone)
                        } else {
                            user.phoneNumber
                        }
                        binding.cardAddress.isVisible = (user.address != Address.EMPTY).also {
                            binding.textAddress.text = user.address.formattedString()
                            binding.textAddressLandmark.text = user.address.landmark
                        }
                        binding.textEmptyState.isVisible = user.address == Address.EMPTY
                    }
                    is Result.Error -> {
                        binding.progressBar.isVisible = false
                        toast(it.error.message ?: it.error.toString())
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                navController.navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupViews() {
        binding.imgUser.load(currentUser.photoUrl) {
            placeholder(R.drawable.ic_person_outline_60)
            transformations(CircleCropTransformation())
        }
        binding.textUserName.text = currentUser.displayName
        binding.textUserEmail.text = currentUser.email
    }

    private fun setupListeners() {
        binding.fabMain.setOnClickListener {
            shouldRotate = it.rotate(!shouldRotate)
            if (shouldRotate) {
                binding.fabPhone.showIn()
                binding.fabAddress.showIn()
            } else {
                binding.fabPhone.showOut()
                binding.fabAddress.showOut()
            }
        }

        binding.fabAddress.setOnClickListener {
            navController.navigate(
                R.id.navigate_to_address_dialog,
                bundleOf(
                    EXTRA_USER to user,
                    EXTRA_FIREBASE_UID to currentUser.uid
                )
            )
        }

        binding.fabPhone.setOnClickListener {
            val dialogBinding = DialogSetPhoneBinding.inflate(LayoutInflater.from(requireContext()))
            dialogBinding.editPhone.setText(user.phoneNumber)

            MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.root)
                .setPositiveButton(R.string.done) { _, _ ->
                    val number = dialogBinding.editPhone.asString().trim()
                    val phoneUtil = PhoneNumberUtil.getInstance()

                    try {
                        if (phoneUtil.isValidNumber(phoneUtil.parse(number, "IN"))) {
                            user.phoneNumber = number
                            viewModel.saveUser(user)
                            sharedPreferences.edit {
                                putBoolean(
                                    PREFERENCE_PROFILE_SET_UP,
                                    user.phoneNumber != "" && user.address != Address.EMPTY
                                )
                            }
                        } else {
                            toast("Verify the number")
                        }
                    } catch (e: NumberParseException) {
                        toast("Verify the number")
                    }
                }
                .setNegativeButton("Cancel") { _, _ ->  }
                .show()
        }
    }
}
