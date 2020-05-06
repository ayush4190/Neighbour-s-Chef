package com.example.myapplication.util.android.base

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Base fragment for enabling ViewBinding support
 * @param <VB> Generated ViewBinding class associated with this fragment
 *
 * onCreateView() contains only 2 lines
 * binding = VB.inflate(inflater, container, false);
 * return binding.getRoot();
 *
 * Views must be setup in onViewCreated()
</VB> */
abstract class BaseFragment<VB : ViewBinding?> : Fragment() {
    protected var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}