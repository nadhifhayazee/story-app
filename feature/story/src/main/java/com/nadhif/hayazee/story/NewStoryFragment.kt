package com.nadhif.hayazee.story

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nadhif.hayazee.baseview.customview.edittext.FormValidator
import com.nadhif.hayazee.baseview.customview.edittext.validator.MustFilledValidator
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.common.util.CameraUtil
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

    companion object {
        const val PHOTO_FILE_URI = "PHOTO_FILE_URI"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoUri = it.getParcelable(PHOTO_FILE_URI)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        setupView()
        observePostStory()
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

            btnUploadStory.setOnClickListener {

                newStoryViewModel.postStory(
                    photoFile,
                    tlDescription.editText?.text.toString()
                )
            }
        }
    }

    private fun setupView() {
        binding.apply {
            photoUri?.let { uri ->
                photoFile = CameraUtil.getRotatedImageFile(
                    CameraUtil.uriToFile(uri, requireContext()),
                    requireContext()
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
}