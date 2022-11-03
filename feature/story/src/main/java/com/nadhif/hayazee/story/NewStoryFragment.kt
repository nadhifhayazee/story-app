package com.nadhif.hayazee.story

import android.Manifest
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.nadhif.hayazee.baseview.customview.edittext.FormValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.MustFilledValidator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.baseview.fragment.navigateDeepLink
import com.nadhif.hayazee.common.Const
import com.nadhif.hayazee.common.extension.getBackStackData
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.common.util.CameraUtil
import com.nadhif.hayazee.common.util.LocationUtil
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.story.databinding.FragmentNewStoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class NewStoryFragment : BaseFragment<FragmentNewStoryBinding>(FragmentNewStoryBinding::inflate) {

    @Inject
    lateinit var newStoryVmFactory: AddNewStoryViewModel.Factory

    private val newStoryViewModel by viewModels<AddNewStoryViewModel> { newStoryVmFactory }

    private var photoUri: Uri? = null
    private var photoFile: File? = null

    private var myLocation: Location? = null

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                // Precise location access granted.
                getLastLocation()
            }
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                // Only approximate location access granted.
                getLastLocation()
            }
            else -> {
                // No location access granted.
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(PHOTO_FILE_URI, Uri::class.java)
            } else {
                it.getParcelable(PHOTO_FILE_URI)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getLastLocation()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSelectedLocation()
        setupListener()
        setupView()
        observePostStory()
    }

    private fun getSelectedLocation() {
        getBackStackData<LatLng>(Const.SELECTED_LOCATION) {
            val location = Location(LocationManager.GPS_PROVIDER)
            location.latitude = it.latitude
            location.longitude = it.longitude
            setupLocationView(location)
        }
    }

    private fun getLastLocation() {
        activity?.let { actvty ->
            if (myLocation == null) {
                LocationUtil.requestEnablingLocationService(actvty)
                LocationUtil.getLastLocation(actvty, object : LocationUtil.LastLocationCallback {
                    override fun onGetLastLocationSuccess(location: Location) {

                        setupLocationView(location)
                    }

                    override fun onGetLastLocationFailed() {

                    }

                    override fun onPermissionNotGranted() {
                        requestPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }

                })
            }
        }
    }

    private fun setupLocationView(location: Location) {
        myLocation = location
        binding.tvLocation.text = LocationUtil.getDistrictSubDistrictName(
            location.latitude, location.longitude, requireActivity()
        )
    }

    private fun observePostStory() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            newStoryViewModel.postStoryState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.progressBar.visible()
                        binding.btnUploadStory.isEnabled = false
                    }

                    is ResponseState.Success -> {
                        binding.progressBar.gone()
                        binding.btnUploadStory.isEnabled = true
                        navigateToHome()

                    }

                    is ResponseState.Error -> {
                        binding.progressBar.gone()
                        binding.btnUploadStory.isEnabled = true
                        binding.root.showErrorSnackBar(it.message)
                    }

                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().popBackStack()
        findNavController().popBackStack()
    }

    private fun setupListener() {
        binding.apply {
            toolbar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnEditLocation.setOnClickListener {
                findNavController().navigateDeepLink("android-app://nadhif.story.app/select_location_fragment/${myLocation?.latitude}/${myLocation?.longitude}".toUri())
            }

            btnUploadStory.setOnClickListener {

                if (myLocation != null) {

                    newStoryViewModel.postStory(
                        photoFile,
                        tlDescription.editText?.text.toString(),
                        myLocation?.latitude,
                        myLocation?.longitude
                    )
                } else {
                    Toast.makeText(
                        requireContext(),
                        resources.getString(com.nadhif.hayazee.common.R.string.location_must_filled),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupView() {
        binding.apply {
            photoUri?.let { uri ->
                photoFile = CameraUtil.getRotatedImageFile(
                    CameraUtil.uriToFile(uri, requireContext()), requireContext()
                )
                val imageBitmap = BitmapFactory.decodeFile(photoFile?.path)
                ivPhoto.setImageBitmap(imageBitmap)


                setupDescriptionValidation()
            }

        }
    }

    private fun setupDescriptionValidation() {
        binding.apply {
            tlDescription.setValidator(MustFilledValidator())
            val formValidator = FormValidator(listOf(tlDescription))
            formValidator.isFormValidated.observe(viewLifecycleOwner) {
                btnUploadStory.isEnabled = it
            }
        }
    }

    companion object {
        const val PHOTO_FILE_URI = "PHOTO_FILE_URI"
    }

}