package com.neighbourschef.vendor.ui.fragment.additems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentAddItemBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.asString
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.isEmpty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.UUID

@ExperimentalCoroutinesApi
class AddItemFragment : BaseFragment<FragmentAddItemBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentBinding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAdd.setOnClickListener {
            if (validateInput()) {
                val day = if (binding.btnToday.isChecked) "Today" else "Tomorrow"

                val product = Product(
                    UUID.randomUUID().toString(),
                    binding.editName.asString().trim(),
                    binding.editPrice.asString().trim().toDouble(),
                    0,
                    binding.switchVeg.isChecked,
                    day
                )
                FirebaseRepository.saveItem(product)
                navController.navigateUp()
            }
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true

        binding.layoutName.error = if (binding.editName.isEmpty()) {
            isValid = false
            binding.layoutName.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        binding.layoutDescription.error = if (binding.editDescription.isEmpty()) {
            isValid = false
            binding.layoutDescription.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        binding.layoutPrice.error = if (binding.editPrice.isEmpty()) {
            isValid = false
            binding.layoutPrice.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        return isValid
    }
}
