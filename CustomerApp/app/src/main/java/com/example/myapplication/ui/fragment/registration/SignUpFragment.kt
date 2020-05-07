package com.example.myapplication.ui.fragment.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.example.myapplication.util.android.base.BaseFragment

class SignUpFragment: BaseFragment<FragmentSignUpBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }
}