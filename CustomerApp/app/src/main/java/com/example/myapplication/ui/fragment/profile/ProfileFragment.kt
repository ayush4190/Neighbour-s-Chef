package com.example.myapplication.ui.fragment.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.CustomerApp
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.model.Address
import com.example.myapplication.model.User
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.common.EXTRA_EMAIL
import com.example.myapplication.util.common.PREFERENCE_PROFILE_SET_UP
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment: BaseFragment<FragmentProfileBinding>(), KodeinAware {
    override val kodein by kodein()
    val app by instance<CustomerApp>()
    val sharedPreferences by instance<SharedPreferences>()

    private val profileViewModel by viewModels<ProfileViewModel> { ProfileViewModelFactory(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgUser.load(app.account?.photoUrl) {
            placeholder(R.drawable.ic_action_name)
            transformations(CircleCropTransformation())
        }
        binding.textUserName.text = app.account?.displayName
        binding.textUserEmail.text = app.account?.email

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.navigate_to_address_dialog,
                bundleOf(EXTRA_EMAIL to app.account?.email)
            )
        }

        lifecycleScope.launch {
            profileViewModel.userWithAddress.collect {
                if (it == null) {
                    binding.recyclerAddresses.visibility = View.INVISIBLE
                    binding.textEmptyState.visibility = View.VISIBLE
                } else {
                    loadViews(it.user, it.addresses)
                }
            }
        }

        sharedPreferences.edit {
            putBoolean(PREFERENCE_PROFILE_SET_UP, true)
        }
//        binding.textProfileUpdateMobile.setOnClickListener {
//            val inflater = requireActivity().layoutInflater
//            val layout = inflater.inflate(
//                R.layout.dialog_set_mobile,
//                it.findViewById<View>(R.id.dialog_mobile) as ViewGroup
//            )
//            AlertDialog.Builder(activity)
//                .setTitle("Please Input Contact Information").setIcon(
//                    android.R.drawable.ic_dialog_dialer
//                ).setView(
//                    layout
//                ).setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, _ ->
//                    val dialog = dialogInterface as Dialog
//                    val inputMobile =
//                        dialog.findViewById<EditText>(R.id.dialog_et_mobile)
//                    if (inputMobile.text.toString().isEmpty()) {
//                        return@OnClickListener
//                    }
//                    try {
//                        val number = inputMobile.text.toString().toLong()
//                    } catch (e: Exception) {
//                        Toast.makeText(
//                            activity,
//                            "Please Input Correct Phone Number!",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }).setNegativeButton("Cancel", null).show()
//        }
//
//        binding.textProfileUpdateAddress.setOnClickListener {
//            binding.textProfileOldPassword.text = "Mobile:"
//            binding.editProfileOldPassword.hint = "Phone Number"
//            binding.textProfileNewPassword.text = "Password:"
//            binding.editProfileNewPassword.hint = "Password"
//            binding.textProfileRetypeNewPassword.text = "New Address"
//            binding.editProfileRetypeNewPassword.hint = "Address"
//            binding.linearLayoutProfilePasswordUpdation.visibility = View.VISIBLE
//            binding.buttonProfileConfirm.setOnClickListener {
//                val phone =
//                    binding.editProfileOldPassword.text.toString()
//                val password =
//                    binding.editProfileNewPassword.text.toString()
//                val address =
//                    binding.editProfileRetypeNewPassword.text.toString()
//            }
//        }
//        binding.linearLayoutProfilePasswordUpdation.visibility = View.INVISIBLE
//        binding.textProfileUpdatePassword.setOnClickListener {
//            binding.textProfileOldPassword.text = "Old Password:"
//            binding.editProfileOldPassword.hint = "Old Password"
//            binding.textProfileNewPassword.text = "New Password:"
//            binding.editProfileNewPassword.hint = "New Password"
//            binding.textProfileRetypeNewPassword.text = "Retype:"
//            binding.editProfileRetypeNewPassword.hint = "Retype Password"
//            binding.linearLayoutProfilePasswordUpdation.visibility = View.VISIBLE
//            binding.buttonProfileConfirm.setOnClickListener {
//                val oldPwd =
//                    binding.editProfileOldPassword.text.toString()
//                val newPwd =
//                    binding.editProfileNewPassword.text.toString()
//                val newPwd2 =
//                    binding.editProfileRetypeNewPassword.text.toString()
//            }
//        }
//        binding.buttonProfileCancel.setOnClickListener {
//            binding.linearLayoutProfilePasswordUpdation.visibility = View.INVISIBLE
//            binding.textProfileOldPassword.text = ""
//            binding.textProfileNewPassword.text = ""
//            binding.textProfileRetypeNewPassword.text = ""
//        }
    }

    private fun loadViews(user: User, addresses: List<Address>) {
        binding.editPhone.setText(PhoneNumberUtils.formatNumber(user.phoneNumber, "in"))

        if (addresses.isEmpty()) {
            binding.recyclerAddresses.visibility = View.INVISIBLE
            binding.textEmptyState.visibility = View.VISIBLE
        } else {
            binding.recyclerAddresses.visibility = View.VISIBLE
            binding.textEmptyState.visibility = View.GONE
        }
        binding.recyclerAddresses.apply {
            adapter = ProfileAdapter(addresses.toMutableList(), findNavController())
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}