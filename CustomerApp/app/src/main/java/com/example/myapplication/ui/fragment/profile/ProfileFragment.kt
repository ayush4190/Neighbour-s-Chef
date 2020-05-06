package com.example.myapplication.ui.fragment.profile

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.util.android.base.BaseFragment

class ProfileFragment: BaseFragment<FragmentProfileBinding>() {
    override fun onResume() {
        super.onResume()
        requireActivity().title = "Profile"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.textProfileUpdateMobile.setOnClickListener {
            val inflater = requireActivity().layoutInflater
            val layout = inflater.inflate(
                R.layout.dialog_set_mobile,
                it.findViewById<View>(R.id.dialog_mobile) as ViewGroup
            )
            AlertDialog.Builder(activity)
                .setTitle("Please Input Contact Information").setIcon(
                    android.R.drawable.ic_dialog_dialer
                ).setView(
                    layout
                ).setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, _ ->
                    val dialog = dialogInterface as Dialog
                    val inputMobile =
                        dialog.findViewById<EditText>(R.id.dialog_et_mobile)
                    if (inputMobile.text.toString().isEmpty()) {
                        return@OnClickListener
                    }
                    try {
                        val number = inputMobile.text.toString().toLong()
                    } catch (e: Exception) {
                        Toast.makeText(
                            activity,
                            "Please Input Correct Phone Number!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }).setNegativeButton("Cancel", null).show()
        }
        
        binding.textProfileUpdateAddress.setOnClickListener {
            binding.textProfileOldPassword.text = "Mobile:"
            binding.editProfileOldPassword.hint = "Phone Number"
            binding.textProfileNewPassword.text = "Password:"
            binding.editProfileNewPassword.hint = "Password"
            binding.textProfileRetypeNewPassword.text = "New Address"
            binding.editProfileRetypeNewPassword.hint = "Address"
            binding.linearLayoutProfilePasswordUpdation.visibility = View.VISIBLE
            binding.buttonProfileConfirm.setOnClickListener {
                val phone =
                    binding.editProfileOldPassword.text.toString()
                val password =
                    binding.editProfileNewPassword.text.toString()
                val address =
                    binding.editProfileRetypeNewPassword.text.toString()
            }
        }
        binding.linearLayoutProfilePasswordUpdation.visibility = View.INVISIBLE
        binding.textProfileUpdatePassword.setOnClickListener {
            binding.textProfileOldPassword.text = "Old Password:"
            binding.editProfileOldPassword.hint = "Old Password"
            binding.textProfileNewPassword.text = "New Password:"
            binding.editProfileNewPassword.hint = "New Password"
            binding.textProfileRetypeNewPassword.text = "Retype:"
            binding.editProfileRetypeNewPassword.hint = "Retype Password"
            binding.linearLayoutProfilePasswordUpdation.visibility = View.VISIBLE
            binding.buttonProfileConfirm.setOnClickListener {
                val oldPwd =
                    binding.editProfileOldPassword.text.toString()
                val newPwd =
                    binding.editProfileNewPassword.text.toString()
                val newPwd2 =
                    binding.editProfileRetypeNewPassword.text.toString()
            }
        }
        binding.buttonProfileCancel.setOnClickListener {
            binding.linearLayoutProfilePasswordUpdation.visibility = View.INVISIBLE
            binding.textProfileOldPassword.text = ""
            binding.textProfileNewPassword.text = ""
            binding.textProfileRetypeNewPassword.text = ""
        }
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}