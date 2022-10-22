package com.nadhif.hayazee.home.story_list

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadhif.hayazee.baseview.fragment.BaseFragment
import com.nadhif.hayazee.baseview.fragment.navigateDeepLink
import com.nadhif.hayazee.common.DeepLinkRoutes
import com.nadhif.hayazee.common.extension.gone
import com.nadhif.hayazee.common.extension.showErrorSnackBar
import com.nadhif.hayazee.common.extension.visible
import com.nadhif.hayazee.home.databinding.FragmentHomeBinding
import com.nadhif.hayazee.home.story_list.adapter.PagingFooterAdapter
import com.nadhif.hayazee.home.story_list.adapter.StoryPagingAdapter
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    StoryPagingAdapter.StorySelectedListener {


    @Inject
    lateinit var homeVmFactory: HomeViewModel.Factory
    private val homeViewModel by viewModels<HomeViewModel> { homeVmFactory }

    private val adapter = StoryPagingAdapter()

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            navigateToAddNewStory()
        } else {
            Toast.makeText(requireContext(), "Check your camera permission!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getStories()
        setupRecyclerView()
        setupView()

        observeVm()

    }

    private fun setupView() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                homeViewModel.getStories()
            }
            btnLogout.setOnClickListener {
                homeViewModel.logout()
                findNavController().popBackStack()
                findNavController().navigateDeepLink(DeepLinkRoutes.loginPage.toUri())
            }

            btnLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }

            btnAddNewStory.setOnClickListener {
                if (!allPermissionsGranted()) {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    navigateToAddNewStory()
                }
            }
        }
    }

    private fun navigateToAddNewStory() {
        findNavController().navigate("android-app://nadhif.story.app/camera_fragment".toUri())
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showShimmer(show: Boolean) {
        binding.apply {
            if (show) {
                rvStory.gone()
                shimmerLayout.visible()
            } else {
                swipeRefreshLayout.isRefreshing = false
                rvStory.visible()
                shimmerLayout.gone()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            adapter.addLoadStateListener { loadState ->
                try {
                    when (val currentState = loadState.source.refresh) {
                        is LoadState.Loading -> {
                            binding.notFoundLayout.gone()
                            showShimmer(true)
                        }
                        is LoadState.NotLoading -> {
                            binding.notFoundLayout.gone()
                            showShimmer(false)
                        }
                        is LoadState.Error -> {
                            showShimmer(false)
                            binding.notFoundLayout.visible()
                            val extractedException = currentState.error
                            extractedException.localizedMessage?.let {
                                binding.root.showErrorSnackBar(
                                    it
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            adapter.storySelectedListener = this@HomeFragment
            rvStory.adapter = adapter.withLoadStateFooter(
                footer = PagingFooterAdapter(
                    onRetryClicked = {
                        adapter.retry()
                    }
                )
            )
            rvStory.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeVm() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.storiesState.collectLatest {
                when (it) {
                    is ResponseState.Loading -> {
                        binding.shimmerLayout.visible()
                    }
                    is ResponseState.Success -> {
                        binding.shimmerLayout.gone()
                        it.data?.let { it1 -> adapter.submitData(it1) }

                    }
                    is ResponseState.Error -> {}
                }
            }
        }
    }

    override fun onStorySelected(story: Story, imageView: ImageView) {
        val action = HomeFragmentDirections.actionHomeFragmentToStoryDetailFragment(story)
        if (story.photoUrl != null) {

            val extras = FragmentNavigatorExtras(
                imageView to story.photoUrl!!,
            )
            findNavController().navigate(action, extras)
        } else {
            findNavController().navigate(action)
        }
    }


}