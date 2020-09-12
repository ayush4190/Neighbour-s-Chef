package com.neighbourschef.customer.util.android.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
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
*/
abstract class BaseFragment<VB : ViewBinding?> : Fragment() {
    protected var currentBinding: VB? = null
    protected val binding: VB
        get() = currentBinding!!

    protected val navController: NavController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }

    abstract override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)
}
