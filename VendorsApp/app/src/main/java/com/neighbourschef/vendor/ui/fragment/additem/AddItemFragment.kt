package com.neighbourschef.vendor.ui.fragment.additem

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.neighbourschef.vendor.MobileNavigationDirections
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentAddItemBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.asString
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.compressImage
import com.neighbourschef.vendor.util.android.getPickImageIntent
import com.neighbourschef.vendor.util.android.isEmpty
import com.neighbourschef.vendor.util.android.snackbar
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.DAY_TODAY
import com.neighbourschef.vendor.util.common.DAY_TOMORROW
import com.neighbourschef.vendor.util.common.RC_PICK_IMAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.UUID

@ExperimentalCoroutinesApi
class AddItemFragment : BaseFragment<FragmentAddItemBinding>() {
    private var imageUri = DEFAULT_URI
    private var cameraPermissionGranted = false

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
        currentBinding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requestPermissions()

        binding.imgFood.load(R.drawable.ic_food_default_64)
        setupListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RC_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data?.data != null) {
                        imageUri = data.data!!
                    } else if (data != null && data.data == null) {
                        toast { "Unable to retrieve image" }
                    }
                    binding.imgFood.load(imageUri) {
                        fallback(R.drawable.ic_food_default_64)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.menu_main, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_help -> {
            navController.navigate(MobileNavigationDirections.navigateToHelp())
            true
        }
        R.id.action_logout -> {
            auth.signOut()
            navController.navigate(MobileNavigationDirections.navigateToLogin())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun requestPermissions() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Above Android Q, only Camera permission is required
            Dexter.withContext(requireContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(
                    object : PermissionListener {
                        override fun onPermissionRationaleShouldBeShown(
                            permission: PermissionRequest?,
                            token: PermissionToken?
                        ) {
                            token?.continuePermissionRequest()
                        }

                        override fun onPermissionGranted(permission: PermissionGrantedResponse?) {
                            cameraPermissionGranted = true
                            toast { "Permissions granted!" }
                        }

                        override fun onPermissionDenied(permission: PermissionDeniedResponse?) =
                            snackbar("Grant permission to take photos of food", "Settings") {
                                startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", requireContext().packageName, null)
                                    )
                                )
                            }
                    }
                )
                .withErrorListener {
                    toast { "Something went wrong while requesting permissions. Please try again" }
                }
                .check()
        } else {
            Dexter.withContext(requireContext())
                .withPermissions(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(
                    object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(permissions: MultiplePermissionsReport?) =
                            if (permissions?.areAllPermissionsGranted() == true) {
                                toast { "Permissions granted!" }
                            } else {
                                snackbar("Grant permissions to continue using the app", "Settings") {
                                    startActivity(
                                        Intent(
                                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                            Uri.fromParts("package", requireContext().packageName, null)
                                        )
                                    )
                                }
                            }

                        override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                        ) {
                            token?.continuePermissionRequest()
                        }
                    }
                )
                .withErrorListener {
                    toast { "Something went wrong while requesting permissions. Please try again" }
                }
                .check()
        }

    private fun validateInput(): Boolean {
        var isValid = true

        binding.layoutName.error = if (binding.editName.isEmpty()) {
            isValid = false
            binding.layoutName.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        binding.layoutPrice.error = if (binding.editPrice.isEmpty()) {
            isValid = false
            binding.layoutPrice.animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
            "Required"
        } else null

        return isValid
    }

    private fun setupListeners() {
        binding.imgFood.setOnClickListener {
            val res = getPickImageIntent(requireContext(), cameraPermissionGranted)
            res.second?.let { imageUri = it }
            requireActivity().startActivityFromFragment(
                this,
                res.first,
                RC_PICK_IMAGE
            )
        }

        binding.btnAdd.setOnClickListener {
            if (validateInput()) {
                val day = if (binding.btnToday.isChecked) DAY_TODAY else DAY_TOMORROW
                val itemName = binding.editName.asString().trim()

                binding.layoutMain.alpha = 0.45f
                binding.layoutProgress.isVisible = true

                // Don't upload default image (already present on customer app)
                if (imageUri == DEFAULT_URI) {
                    FirebaseRepository.saveItem(
                        Product(
                            UUID.randomUUID().toString(),
                            itemName,
                            binding.editDescription.asString().trim(),
                            binding.editPrice.asString().trim().toDouble(),
                            0,
                            binding.switchVeg.isChecked,
                            day,
                            null
                        )
                    )
                    navController.navigateUp()
                } else {
                    // Upload compressed image to Firebase
                    lifecycleScope.launch {
                        FirebaseRepository.uploadImage(
                            itemName,
                            compressImage(requireContext(), imageUri).toByteArray()
                        ).addOnProgressListener {
                            val progress = it.bytesTransferred * 100.0 / it.totalByteCount
                            binding.textProgress.text = requireContext().getString(R.string.uploading, progress)
                            binding.progressBar.progress = progress.toInt()
                        }.addOnFailureListener {
                            toast { it.message ?: it.toString() }
                            binding.layoutProgress.isVisible = false
                            binding.layoutMain.alpha = 1f
                        }.addOnSuccessListener {
                            toast { "Upload Complete" }
                            binding.layoutProgress.isVisible = false
                            binding.layoutMain.alpha = 1f
                        }.continueWithTask {
                            if (!it.isSuccessful) {
                                it.exception?.let { e -> throw e }
                            }
                            FirebaseRepository.getDownloadUrl(itemName)
                        }.addOnCompleteListener {
                            if (it.isSuccessful) {
                                val product = Product(
                                    UUID.randomUUID().toString(),
                                    itemName,
                                    binding.editDescription.asString().trim(),
                                    binding.editPrice.asString().trim().toDouble(),
                                    0,
                                    binding.switchVeg.isChecked,
                                    day,
                                    it.result.toString()
                                )
                                FirebaseRepository.saveItem(product)
                                navController.navigateUp()
                            } else {
                                toast { it.exception?.message ?: it.exception.toString() }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private val DEFAULT_URI =
            "android.resource://${R::class.java.`package`!!.name}/${R.drawable.ic_food_default_64}".toUri()
    }
}
