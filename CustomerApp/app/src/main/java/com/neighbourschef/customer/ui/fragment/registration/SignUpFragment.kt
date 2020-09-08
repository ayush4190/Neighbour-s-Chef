package com.neighbourschef.customer.ui.fragment.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neighbourschef.customer.databinding.FragmentSignUpBinding
import com.neighbourschef.customer.util.android.base.BaseFragment

class SignUpFragment: BaseFragment<FragmentSignUpBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): SignUpFragment = SignUpFragment()
    }
}