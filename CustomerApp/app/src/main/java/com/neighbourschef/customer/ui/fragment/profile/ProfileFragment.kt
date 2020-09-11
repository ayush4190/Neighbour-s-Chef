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
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogSetPhoneBinding
import com.neighbourschef.customer.databinding.FragmentProfileBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.getUserRef
import com.neighbourschef.customer.util.android.init
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.rotate
import com.neighbourschef.customer.util.android.showIn
import com.neighbourschef.customer.util.android.showOut
import com.neighbourschef.customer.util.android.toast
import com.neighbourschef.customer.util.common.EXTRA_USER
import com.neighbourschef.customer.util.common.PREFERENCE_PROFILE_SET_UP
import com.neighbourschef.customer.util.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class ProfileFragment: BaseFragment<FragmentProfileBinding>(), DIAware {
    override val di by di()
    val app by instance<CustomerApp>()
    val sharedPreferences by instance<SharedPreferences>()

    private val ref: String by lazy(LazyThreadSafetyMode.NONE) { getUserRef(sharedPreferences)!! }

    private val viewModel: ProfileViewModel by viewModels { ProfileViewModelFactory(ref) }

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
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddress.init()
        binding.fabPhone.init()

        setupViews()
        setupListeners()

        viewModel.user.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> binding.progressBar.isVisible = true
                is UiState.Success<*> -> {
                    binding.progressBar.isVisible = false
                    user = it.data as User

                    binding.textUserPhone.text = if (user.phoneNumber == "") {
                        getString(R.string.empty_phone)
                    } else {
                        user.phoneNumber
                    }
                    binding.cardAddress.isVisible = (user.address != Address.EMPTY).also {
                        binding.textAddressName.text = user.address.addressName
                        binding.textAddress.text = user.address.formattedString()
                        binding.textAddressLandmark.text = user.address.landmark
                    }
                    binding.textEmptyState.isVisible = user.address == Address.EMPTY
                }
                is UiState.Failure -> {
                    binding.progressBar.isVisible = false
                    toast(it.reason)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                app.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun setupViews() {
        binding.imgUser.load(app.account?.photoUrl) {
            placeholder(R.drawable.ic_profile_placeholder)
            transformations(CircleCropTransformation())
        }
        binding.textUserName.text = app.account?.displayName
        binding.textUserEmail.text = app.account?.email
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
            findNavController().navigate(
                R.id.navigate_to_address_dialog,
                bundleOf(EXTRA_USER to user)
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
