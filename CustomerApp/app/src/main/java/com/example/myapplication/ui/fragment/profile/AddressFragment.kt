package com.example.myapplication.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.myapplication.MobileNavigationDirections
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentAddressBinding
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.Address
import com.example.myapplication.model.User
import com.example.myapplication.util.android.asString
import com.example.myapplication.util.common.EXTRA_USER
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AddressFragment: BottomSheetDialogFragment(), KodeinAware {
    override val kodein by kodein()
    val database by instance<CustomerDatabase>()

    private var _binding: FragmentAddressBinding? = null
    private val binding: FragmentAddressBinding
        get() = _binding!!

    private val user by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_USER] as User
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadViews()

        binding.btnDone.setOnClickListener {
            val newAddress = validateInput()
            if (newAddress != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    user.address = newAddress
                    database.userDao().update(user)
                }
                findNavController().navigate(
                    MobileNavigationDirections.navigateToProfile(),
                    navOptions {
                        popUpTo(R.id.nav_profile) {
                            inclusive = true
                        }
                    }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadViews() {
        if (user.address != Address.EMPTY) {
            with(binding) {
                editAddressName.setText(user.address.addressName)
                editAddressFlat.setText(user.address.flatNo)
                editAddressBuilding.setText(user.address.building)
                editAddressStreet.setText(user.address.street)
                editAddressLocality.setText(user.address.locality)
                editAddressCity.setText(user.address.city)
                editAddressPinCode.setText(user.address.pinCode)
                editAddressLandmark.setText(user.address.landmark)
            }
        }
    }

    private fun validateInput(): Address? {
        var isValid = false

        val name = binding.editAddressName.asString().trim()
        binding.layoutAddressName.error = if (name.isEmpty()) "Required" else null

        val flat = binding.editAddressFlat.asString().trim().let { if (it.isEmpty()) null else it }

        val building = binding.editAddressBuilding.asString().trim()
        binding.layoutAddressBuilding.error = if (building.isEmpty()) "Required" else null

        val street = binding.editAddressStreet.asString().trim()
        binding.layoutAddressStreet.error = if (street.isEmpty()) "Required" else null

        val locality = binding.editAddressLocality.asString().trim()
        binding.layoutAddressLocality.error = if (locality.isEmpty()) "Required" else null

        val city = binding.editAddressCity.asString().trim()
        binding.layoutAddressCity.error = if (city.isEmpty()) "Required" else null

        val pinCode = binding.editAddressPinCode.asString().trim()
        binding.layoutAddressPinCode.error = when {
                pinCode.isEmpty() -> "Required"
                pinCode.length != 6 -> "Must be 6 digits"
                else -> {
                    isValid = true
                    null
                }
            }

        val landmark = binding.editAddressLandmark.asString().trim().let { if (it.isEmpty()) null else it }

        return if (isValid) Address(name, flat, building, street, locality, city, pinCode, landmark) else null
    }
}