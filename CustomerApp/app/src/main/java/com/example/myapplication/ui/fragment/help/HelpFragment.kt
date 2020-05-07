package com.example.myapplication.ui.fragment.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.myapplication.util.android.asString
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.sendEmail

class HelpFragment: BaseFragment<FragmentHelpBinding>() {
    override fun onResume() {
        super.onResume()
        requireActivity().title = "Help"
    }

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
        binding.helpEmailSend.setOnClickListener {
            sendEmail(
                requireContext(),
                binding.helpEmailSubject.asString(),
                binding.helpEmailContent.asString()
            )
        }
    }
}