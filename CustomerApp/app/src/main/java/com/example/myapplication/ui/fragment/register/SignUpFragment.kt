package com.example.myapplication.ui.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.example.myapplication.util.android.base.BaseFragment

class SignUpFragment private constructor() :
    BaseFragment<FragmentSignUpBinding?>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(
            inflater,
            container,
            false
        )
        return binding!!.getRoot()
    }

    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}