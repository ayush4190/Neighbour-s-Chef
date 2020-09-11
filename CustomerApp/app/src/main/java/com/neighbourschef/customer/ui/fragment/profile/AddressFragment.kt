package com.neighbourschef.customer.ui.fragment.profile

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neighbourschef.customer.R
import com.neighbourschef.customer.databinding.FragmentAddressBinding
import com.neighbourschef.customer.model.Address
import com.neighbourschef.customer.model.User
import com.neighbourschef.customer.repositories.FirebaseRepository
import com.neighbourschef.customer.util.android.asString
import com.neighbourschef.customer.util.common.EXTRA_FIREBASE_UID
import com.neighbourschef.customer.util.common.EXTRA_USER
import com.neighbourschef.customer.util.common.PREFERENCE_PROFILE_SET_UP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

@ExperimentalCoroutinesApi
class AddressFragment: BottomSheetDialogFragment(), DIAware {
    override val di by di()
    val sharedPreferences by instance<SharedPreferences>()

    private var currentBinding: FragmentAddressBinding? = null
    private val binding: FragmentAddressBinding
        get() = currentBinding!!

    private val user by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_USER] as User
    }
    private val uid by lazy(LazyThreadSafetyMode.NONE) {
        requireArguments()[EXTRA_FIREBASE_UID] as String
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentAddressBinding.inflate(inflater, container, false)
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
                    FirebaseRepository.saveUser(user, uid)
                }
                sharedPreferences.edit {
                    putBoolean(
                        PREFERENCE_PROFILE_SET_UP,
                        user.phoneNumber != "" && user.address != Address.EMPTY
                    )
                }
                findNavController().navigateUp()
                // findNavController().navigate(
                //     MobileNavigationDirections.navigateToProfile(),
                //     navOptions {
                //         popUpTo(R.id.nav_profile) {
                //             inclusive = true
                //         }
                //     }
                // )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
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
        binding.layoutAddressName.error = if (name.isEmpty()) {
            binding.layoutAddressName.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        val flat = binding.editAddressFlat.asString().trim().let { if (it.isEmpty()) null else it }

        val building = binding.editAddressBuilding.asString().trim()
        binding.layoutAddressBuilding.error = if (building.isEmpty()) {
            binding.layoutAddressBuilding.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        val street = binding.editAddressStreet.asString().trim()
        binding.layoutAddressStreet.error = if (street.isEmpty()) {
            binding.layoutAddressStreet.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        val locality = binding.editAddressLocality.asString().trim()
        binding.layoutAddressLocality.error = if (locality.isEmpty()) {
            binding.layoutAddressLocality.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        val city = binding.editAddressCity.asString().trim()
        binding.layoutAddressCity.error = if (city.isEmpty()) {
            binding.layoutAddressCity.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        val pinCode = binding.editAddressPinCode.asString().trim()
        binding.layoutAddressPinCode.error = when {
                pinCode.isEmpty() -> {
                    binding.layoutAddressPinCode.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                    "Required"
                }
                pinCode.length != 6 -> {
                    binding.layoutAddressPinCode.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                    "Must be 6 digits"
                }
                else -> {
                    isValid = true
                    null
                }
            }

        val landmark = binding.editAddressLandmark.asString().trim().let { if (it.isEmpty()) null else it }

        return if (isValid) Address(name, flat, building, street, locality, city, pinCode, landmark) else null
    }
}
