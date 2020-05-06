package com.example.myapplication.ui.fragment.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentHelpBinding
import com.example.myapplication.util.android.base.BaseFragment
import com.example.myapplication.util.android.sendEmail

class HelpFragment private constructor() :
    BaseFragment<FragmentHelpBinding?>() {
    override fun onResume() {
        super.onResume()
        requireActivity().title = "Help"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHelpBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.helpEmailSend.setOnClickListener { view1: View? ->
            sendEmail(
                requireContext(),
                binding!!.helpEmailSubject.text.toString(),
                binding!!.helpEmailContent.text.toString()
            )
        }
    }

    companion object {
        fun newInstance(): HelpFragment {
            return HelpFragment()
        }
    }
}