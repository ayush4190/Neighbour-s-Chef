package com.example.myapplication.ui.fragment.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.myapplication.util.android.asString
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.sendEmail
import com.example.myapplication.util.common.DEV_EMAIL
import com.example.myapplication.util.common.DEV_NUMBER

class HelpFragment: BaseFragment<FragmentHelpBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textContact.text = getString(R.string.call_us, DEV_NUMBER)
        binding.textEmail.text = getString(R.string.email_us, DEV_EMAIL)

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$DEV_NUMBER")
            }
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }

        binding.btnSend.setOnClickListener {
            sendEmail(
                requireContext(),
                binding.inputSubject.asString().trim(),
                binding.inputBody.asString().trim()
            )
        }
    }
}