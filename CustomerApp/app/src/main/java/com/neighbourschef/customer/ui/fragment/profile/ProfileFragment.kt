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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.DialogSetPhoneBinding
import com.neighbourschef.customer.databinding.FragmentProfileBinding
import com.neighbourschef.customer.db.CustomerDatabase
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.init
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.rotate
import com.neighbourschef.customer.util.android.showIn
import com.neighbourschef.customer.util.android.showOut
import com.neighbourschef.customer.util.common.EXTRA_USER
import com.neighbourschef.customer.util.common.PREFERENCE_PROFILE_SET_UP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class ProfileFragment: BaseFragment<FragmentProfileBinding>(), DIAware {
    override val di by di()
    val app by instance<CustomerApp>()
    val database by instance<CustomerDatabase>()
    val sharedPreferences by instance<SharedPreferences>()

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

        lifecycleScope.launch {
            user = database.userDao().getUserByEmail(app.account!!.email!!)

            binding.textUserPhone.text = user.phoneNumber
            binding.cardAddress.isVisible = (user.address != Address.EMPTY).also {
                binding.textAddressName.text = user.address.addressName
                binding.textAddress.text = user.address.formattedString()
                binding.textAddressLandmark.text = user.address.landmark
            }
            binding.textEmptyState.isVisible = user.address == Address.EMPTY
        }
        binding.imgUser.load(app.account?.photoUrl) {
            placeholder(R.drawable.ic_profile_placeholder)
            transformations(CircleCropTransformation())
        }
        binding.textUserName.text = app.account?.displayName
        binding.textUserEmail.text = app.account?.email

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
            MaterialAlertDialogBuilder(requireContext())
                .setView(dialogBinding.root)
                .setPositiveButton(R.string.done) { dialog, _ ->
                    user.phoneNumber = dialogBinding.editPhone.asString().trim()
                    binding.textUserPhone.text = user.phoneNumber
                    lifecycleScope.launch(Dispatchers.IO) {
                        database.userDao().update(user)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        sharedPreferences.edit {
            putBoolean(PREFERENCE_PROFILE_SET_UP, true)
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
}
