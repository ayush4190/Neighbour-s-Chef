package com.example.myapplication.ui.fragment.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.CustomerApp
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.DialogSetPhoneBinding
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.Address
import com.example.myapplication.model.User
import com.example.myapplication.util.android.*
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_USER
import com.example.myapplication.util.common.PREFERENCE_PROFILE_SET_UP
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment: BaseFragment<FragmentProfileBinding>(), KodeinAware {
    override val kodein by kodein()
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddress.init()
        binding.fabPhone.init()

        lifecycleScope.launch {
            user = database.userDao().getUserById(app.account!!.email!!)

            binding.textUserPhone.text = user.phoneNumber
            binding.cardAddress.showIf(user.address != Address.EMPTY).also {
                binding.textAddressName.text = user.address.addressName
                binding.textAddress.text = user.address.formattedString()
                binding.textAddressLandmark.text = user.address.landmark
            }
            binding.textEmptyState.showIf(user.address == Address.EMPTY)
        }
        binding.imgUser.load(app.account?.photoUrl) {
            placeholder(R.drawable.ic_action_name)
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