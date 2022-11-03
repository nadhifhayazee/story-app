package com.nadhif.hayazee.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.nadhif.hayazee.domain.auth.LogoutUseCase
import com.nadhif.hayazee.domain.story.GetStoriesPagingUseCase
import com.nadhif.hayazee.home.story_list.HomeViewModel
import com.nadhif.hayazee.home.story_list.adapter.StoryPagingAdapter
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.common.Story
import com.nadhif.hayazee.model.dummies.HomeDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()


    @Mock
    lateinit var getStoriesUseCase: GetStoriesPagingUseCase

    @Mock
    lateinit var logoutUseCase: LogoutUseCase


    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(getStoriesUseCase, logoutUseCase)
    }

    @Test

    fun `when get stories paging success`() = runTest {
        val dummyStories = HomeDummy.getDummyStoryList()
        val data = PageTestDataSource.snapshot(dummyStories ?: arrayListOf())

        `when`(getStoriesUseCase()).then {
            flow {
                emit(data)
            }
        }

        val states = mutableListOf<ResponseState<PagingData<Story>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.storiesState.toList(states)
        }

        homeViewModel.getStories()

        val result = (states.last() as ResponseState.Success).data

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.diffUtil,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.testDispatcher,
            workerDispatcher = mainCoroutineRule.testDispatcher
        )

        differ.submitData(result!!)

        assert(states.last() is ResponseState.Success)
        assertNotNull(differ.snapshot())
        assertEquals(differ.snapshot().size, dummyStories?.size)

        job.cancel()
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }


    @Test
    fun `get story paging data empty`() = runTest {
        val data = PageTestDataSource.snapshot(listOf())

        `when`(getStoriesUseCase()).then {
            flow {
                emit(data)
            }
        }

        val states = mutableListOf<ResponseState<PagingData<Story>>>()
        val job = launch(UnconfinedTestDispatcher()) {
            homeViewModel.storiesState.toList(states)
        }

        homeViewModel.getStories()

        val result = (states.last() as ResponseState.Success).data

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryPagingAdapter.diffUtil,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainCoroutineRule.testDispatcher,
            workerDispatcher = mainCoroutineRule.testDispatcher
        )

        differ.submitData(result!!)

        assert(states.last() is ResponseState.Success)
        assertEquals(0, differ.snapshot().size)

        job.cancel()

    }
}