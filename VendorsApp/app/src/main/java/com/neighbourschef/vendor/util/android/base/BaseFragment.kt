package com.neighbourschef.vendor.util.android.base

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
    protected var currentBinding: VB? = null
    protected var binding: VB
        get() = currentBinding!!

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }
}
