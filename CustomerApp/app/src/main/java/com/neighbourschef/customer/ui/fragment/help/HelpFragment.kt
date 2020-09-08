package com.neighbourschef.customer.ui.fragment.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.findNavController
import com.neighbourschef.customer.CustomerApp
import com.neighbourschef.customer.MobileNavigationDirections
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentHelpBinding
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.android.base.BaseFragment
import com.neighbourschef.customer.util.android.restartApp
import com.neighbourschef.customer.util.android.sendEmail
import com.neighbourschef.customer.util.common.DEV_EMAIL
import com.neighbourschef.customer.util.common.DEV_NUMBER
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class HelpFragment: BaseFragment<FragmentHelpBinding>(), DIAware {
    override val di by di()
    val app by instance<CustomerApp>()

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
        super.onViewCreated(view, savedInstanceState)

        binding.textContact.text = getString(R.string.call_us, DEV_NUMBER)
        binding.textEmail.text = getString(R.string.email_us, DEV_EMAIL)

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$DEV_NUMBER")
            }
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) =
        inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(MobileNavigationDirections.navigateToSettings())
                true
            }
            R.id.action_logout -> {
                app.signOut()
                restartApp(requireActivity())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}