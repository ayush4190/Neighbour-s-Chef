package com.neighbourschef.vendor.ui.fragment.additem

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.net.toUri
import coil.load
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.neighbourschef.vendor.R
import com.neighbourschef.vendor.databinding.FragmentAddItemBinding
import com.neighbourschef.vendor.model.Product
import com.neighbourschef.vendor.repository.FirebaseRepository
import com.neighbourschef.vendor.util.android.asString
import com.neighbourschef.vendor.util.android.base.BaseFragment
import com.neighbourschef.vendor.util.android.isEmpty
import com.neighbourschef.vendor.util.android.snackbar
import com.neighbourschef.vendor.util.android.toast
import com.neighbourschef.vendor.util.common.RC_PICK_IMAGE
import com.neighbourschef.vendor.util.common.log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.UUID

@ExperimentalCoroutinesApi
class AddItemFragment : BaseFragment<FragmentAddItemBinding>() {
    private var imageUri = DEFAULT_URI
    private var cameraPermissionGranted = false

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

        binding.imgFood.load(R.drawable.ic_food_bowl_64)
        binding.imgFood.setOnClickListener {
            requireActivity().startActivityFromFragment(
                this,
                getPickImageIntent(),
                RC_PICK_IMAGE
            )
        }

        binding.btnAdd.setOnClickListener {
            if (validateInput()) {
                val day = if (binding.btnToday.isChecked) "Today" else "Tomorrow"

                val product = Product(
                    UUID.randomUUID().toString(),
                    binding.editName.asString().trim(),
                    binding.editDescription.asString().trim(),
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

                    // File(imageUri.path!!).length().log("Size:")
                    val original = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(requireContext().contentResolver, imageUri)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
                    }
                    val out = ByteArrayOutputStream()
                    original.compress(Bitmap.CompressFormat.JPEG, 50, out)
                    val compressed = BitmapFactory.decodeStream(ByteArrayInputStream(out.toByteArray()))
                    binding.imgFood.load(imageUri) {
                        fallback(R.drawable.ic_food_bowl_64)
                    }
                }
            }
        }
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
                    it.log()
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
                    it.log()
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

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null
        val intentList = mutableListOf<Intent>()

        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        addIntentToList(intentList, galleryIntent)
        if (cameraPermissionGranted) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())
            addIntentToList(intentList, cameraIntent)
        }

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                intentList.removeLast(),
                requireContext().getString(R.string.select_capture_image)
            ).putExtra(
                Intent.EXTRA_INITIAL_INTENTS,
                intentList.toTypedArray()
            )
        }
        return chooserIntent
    }

    private fun addIntentToList(intents: MutableList<Intent>, intent: Intent): MutableList<Intent> {
        val resInfo = requireContext().packageManager.queryIntentActivities(intent, 0)
        for (info in resInfo) {
            intents.add(
                Intent(intent).setPackage(info.activityInfo.packageName)
            )
        }
        return intents
    }

    private fun setImageUri(): Uri {
        val resolver = requireContext().contentResolver
        val contentValues = ContentValues(3).apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, getTempFileName())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        // Below Android Q, images are stored in private data directory which will be removed on uninstall

        // val directory = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString())
        // directory.mkdirs()
        //
        // val file = File(
        //     directory,
        //     getTempFileName()
        // )
        // if (file.exists()) file.delete()
        // file.createNewFile()
        // imageUri = FileProvider.getUriForFile(
        //     requireContext(),
        //     BuildConfig.APPLICATION_ID + requireContext().getString(R.string.file_provider_name),
        //     file
        // )
        imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: DEFAULT_URI
        return imageUri
    }

    private fun getTempFileName(): String =
        "JPEG_${LocalDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))}.jpg"

    companion object {
        private val DEFAULT_URI =
            "android.resource://${R::class.java.`package`!!.name}/${R.drawable.ic_food_bowl_64}".toUri()
    }
}
