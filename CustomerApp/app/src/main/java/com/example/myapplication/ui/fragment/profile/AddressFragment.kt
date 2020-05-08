package com.example.myapplication.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentAddressBinding
import com.example.myapplication.db.CustomerDatabase
import com.example.myapplication.model.Address
import com.example.myapplication.util.android.asString
import com.example.myapplication.util.common.EXTRA_ADDRESS
import com.example.myapplication.util.common.EXTRA_EMAIL
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

    private val email by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_EMAIL] as String
    }
    private val address by lazy(LazyThreadSafetyMode.NONE) {
        arguments?.getParcelable<Address>(EXTRA_ADDRESS)
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
                    database.addressDao().insert(newAddress)
                }
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadViews() {
        address?.let {
            with(binding) {
                editAddressName.setText(it.name)
                editAddressFlat.setText(it.flatNo)
                editAddressBuilding.setText(it.building)
                editAddressStreet.setText(it.street)
                editAddressLocality.setText(it.locality)
                editAddressCity.setText(it.city)
                editAddressPinCode.setText(it.pinCode)
                editAddressLandmark.setText(it.landmark)
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
        binding.layoutAddressPinCode.error = if (pinCode.isEmpty()) "Required" else {
            isValid = true
            null
        }

        val landmark = binding.editAddressFlat.asString().trim().let { if (it.isEmpty()) null else it }

        return if (isValid) Address(name, email, flat, building, street, locality, city, pinCode, landmark) else null
    }
}