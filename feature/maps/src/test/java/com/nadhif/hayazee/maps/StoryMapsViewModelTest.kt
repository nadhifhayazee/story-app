package com.nadhif.hayazee.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.domain.story.GetStoriesUseCase
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryMapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()


    @Mock
    lateinit var getStoriesUseCase: GetStoriesUseCase

    private lateinit var mapsViewModel: StoryMapsViewModel

    @Before
    fun setup() {
        mapsViewModel = StoryMapsViewModel(getStoriesUseCase)
    }

    @Test()
    fun `get stories success`() = runTest {
        val expectedResult = ResponseState.Success(HomeDummy.getDummyStoryList())

        `when`(getStoriesUseCase(null, null, 1)).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<List<Story>>>()
        val job = launch(UnconfinedTestDispatcher()) {

            mapsViewModel.state.toList(states)
        }

        mapsViewModel.getStories()

        assert(states.last() is ResponseState.Success)
        assertEquals(
            expectedResult.data?.size,
            (states.last() as ResponseState.Success).data?.size
        )

        job.cancel()
    }

    @Test()
    fun `get stories failed`() = runTest {
        val errorMessageDummy = "Token already expired"
        val expectedResult = ResponseState.Error<List<Story>>(errorMessageDummy)

        `when`(getStoriesUseCase(null, null, 1)).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<List<Story>>>()
        val job = launch(UnconfinedTestDispatcher()) {

            mapsViewModel.state.toList(states)
        }

        mapsViewModel.getStories()

        assert(states.last() is ResponseState.Error)
        assertEquals(
            errorMessageDummy,
            (states.last() as ResponseState.Error).message
        )

        job.cancel()
    }


}