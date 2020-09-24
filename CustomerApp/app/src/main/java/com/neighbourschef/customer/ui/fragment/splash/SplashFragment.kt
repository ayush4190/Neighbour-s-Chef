package com.neighbourschef.customer.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navOptions
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.databinding.FragmentSplashBinding
import com.neighbourschef.customer.util.android.base.BaseFragment
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenCreated {
            delay(2000)
            navController.navigate(
                MobileNavigationDirections.navigateToRegistration(),
                navOptions {
                    anim {
                        exit = android.R.anim.slide_out_right
                    }
                }
            )
        }
    }
}
