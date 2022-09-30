package com.nadhif.hayazee.home.story_detail

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.common.extension.loadImage
import com.nadhif.hayazee.common.util.DateUtil
import com.nadhif.hayazee.home.databinding.FragmentStoryDetailBinding


class StoryDetailFragment :
    BaseFragment<FragmentStoryDetailBinding>(FragmentStoryDetailBinding::inflate) {

    private val args by navArgs<StoryDetailFragmentArgs>()
//    private var story: Story? = null
//
//    companion object {
//        const val STORY_DATA = "STORY_DATA"
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = context?.let {
            TransitionInflater.from(it).inflateTransition(android.R.transition.move)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupAppBar()

    }

    private fun setupAppBar() {
        binding.apply {
            appBar.btnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupView() {
        binding.apply {
            ivStoryPhoto.apply {
                transitionName = args.story?.photoUrl
                loadImage(args.story?.photoUrl)
            }
            tvUserName.text = args.story?.name
            tvDescription.text = args.story?.description
            tvCreatedDate.text = DateUtil.convertDateBasedOnLocale(
                args.story?.createdAt ?: "",
                DateUtil.TIME_FORMAT_WITH_TZ,
                DateUtil.EEEE_dd_MMM_yyyy_HH_mm,
                requireContext()
            )
        }
    }

}