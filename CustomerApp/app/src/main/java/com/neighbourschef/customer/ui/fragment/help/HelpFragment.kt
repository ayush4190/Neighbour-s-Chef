package com.neighbourschef.customer.ui.fragment.help

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import coil.load
import coil.transform.CircleCropTransformation
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentHelpBinding
import com.neighbourschef.customer.ui.activity.MainActivity
import com.neighbourschef.customer.util.android.CircleBorderTransformation
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.sendEmail
import com.neighbourschef.customer.util.common.NUMBER_DEV

class HelpFragment: BaseFragment<FragmentHelpBinding>() {
    private val auth by lazy(LazyThreadSafetyMode.NONE) { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, "tel:$NUMBER_DEV".toUri())
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_help, menu)

        val menuItem = menu.findItem(R.id.action_profile)!!
        val imageView = menuItem.actionView?.findViewById<ImageView>(R.id.img_user_account)!!
        menuItem.actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        imageView.load(auth.currentUser?.photoUrl) {
            transformations(CircleCropTransformation(), CircleBorderTransformation())
            placeholder(R.drawable.ic_person_outline_24)
            fallback(R.drawable.ic_person_outline_24)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_profile -> {
                navController.navigate(MobileNavigationDirections.navigateToProfile())
                true
            }
            R.id.action_logout -> {
                auth.signOut()
                (requireActivity() as MainActivity).googleSignInClient.signOut()
                navController.navigate(MobileNavigationDirections.navigateToRegistration())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
