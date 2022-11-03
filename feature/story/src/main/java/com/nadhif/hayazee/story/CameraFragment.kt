package com.nadhif.hayazee.story

import android.app.Activity
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.util.CameraUtil.createFile
import com.nadhif.hayazee.story.databinding.FragmentCameraBinding

class CameraFragment : BaseFragment<FragmentCameraBinding>(FragmentCameraBinding::inflate) {

    private var imageCapture: ImageCapture? = null
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            navigateToAddNewStoryFragment(selectedImg)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViewListener()
        startCamera()
    }

    private fun setupViewListener() {
        binding.apply {
            btnTakeCamera.setOnClickListener {
                takePhoto()

            }
            btnSwitchCamera.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
            }

            btnGallery.setOnClickListener {
                startGallery()
            }

            toolbar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun takePhoto() {
        activity?.application?.let { application ->

            val imageCapture = imageCapture ?: return

            val photoFile = createFile(application)

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
            imageCapture.takePicture(outputOptions,
                ContextCompat.getMainExecutor(requireActivity()),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Toast.makeText(
                            requireActivity(), "Gagal mengambil gambar.", Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        navigateToAddNewStoryFragment(output.savedUri)
                    }
                })
        }
    }

    private fun navigateToAddNewStoryFragment(
        photoUri: Uri?
    ) {
        val bundle = Bundle()
        bundle.putParcelable(NewStoryFragment.PHOTO_FILE_URI, photoUri)
        findNavController().navigate(R.id.action_camera_fragment_to_newStoryFragment, bundle)
    }

    private fun startCamera() {
        activity?.let { actvty ->
            val cameraProviderFuture = ProcessCameraProvider.getInstance(actvty)

            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(binding.cameraView.surfaceProvider)
                }

                imageCapture = ImageCapture.Builder().build()

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture
                    )
                } catch (exc: Exception) {
                    Toast.makeText(
                        activity,
                        resources.getString(com.nadhif.hayazee.common.R.string.open_camera_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }, ContextCompat.getMainExecutor(actvty))

        }
    }

}