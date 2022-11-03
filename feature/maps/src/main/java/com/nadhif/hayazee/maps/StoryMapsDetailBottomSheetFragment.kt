package com.nadhif.hayazee.maps

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.loadImage
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.common.util.DateUtil
import com.nadhif.hayazee.common.util.LocationUtil
import com.nadhif.hayazee.maps.databinding.FragmentStoryMapsDetailBottomSheetBinding
import com.nadhif.hayazee.model.common.Story

class StoryMapsDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentStoryMapsDetailBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var story: Story? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            story = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARGS_STORY, Story::class.java)
            } else {
                it.getParcelable(ARGS_STORY)
            }
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundedBottomSheetDialogThemeWhite)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStoryMapsDetailBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()

    }

    private fun setupView() {
        binding.apply {
            tvUserName.text = story?.name
            tvDescription.text = story?.description
            tvCreatedDate.text = DateUtil.convertDateBasedOnLocale(
                story?.createdAt ?: "",
                DateUtil.TIME_FORMAT_WITH_TZ,
                DateUtil.EEEE_dd_MMM_yyyy_HH_mm,
                binding.root.context
            )
            ivStoryPhoto.apply {
                transitionName = story?.photoUrl
                loadImage(story?.photoUrl)
            }

            val location = LocationUtil.getDistrictSubDistrictName(
                story?.lat ?: 0.0, story?.lon ?: 0.0,
                binding.root.context
            )
            if (location != null) {
                tvLocation.text = location
                tvLocation.visible()
                ivLocation.visible()
            } else {
                tvLocation.gone()
                ivLocation.gone()
            }

        }
    }

    companion object {
        const val ARGS_STORY = "ARGS_STORY"

        @JvmStatic
        fun newInstance(story: Story) =
            StoryMapsDetailBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARGS_STORY, story)
                }
            }
    }
}