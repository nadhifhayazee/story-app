package com.nadhif.hayazee.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.domain.story.PostStoryUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.response.RegisterResponse
import com.nadhif.hayazee.model.dummies.DummyAddNewStory
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
import java.io.File


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddNewStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()


    @Mock
    lateinit var postStoryUseCase: PostStoryUseCase

    lateinit var addNewStoryViewModel: AddNewStoryViewModel

    @Before
    fun setup() {
        addNewStoryViewModel = AddNewStoryViewModel(postStoryUseCase)
    }

    @Test
    fun `post new story success`() = runTest {

        val expectedResult = ResponseState.Success(DummyAddNewStory.getDummySuccessPostStory())

        val file = File("images/photo.jpg")
        val description = "Hidup kadang tidak adil."
        val lat = -123.4212
        val lon = 121.331

        `when`(postStoryUseCase(file, description, lat, lon)).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<RegisterResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            addNewStoryViewModel.postStoryState.toList(states)
        }

        addNewStoryViewModel.postStory(
            file, description, lat, lon
        )

        assert(states.last() is ResponseState.Success)
        assert((states.last() as ResponseState.Success).data?.error == false)
        assertEquals(
            expectedResult.data?.message,
            (states.last() as ResponseState.Success).data?.message
        )

        job.cancel()
    }

    @Test
    fun `post new story failed`() = runTest {

        val dummyErrorMessage = "Story failed to create!"
        val expectedResult = ResponseState.Error<RegisterResponse>(dummyErrorMessage)

        val file = File("images/photo.jpg")
        val description = "Hidup kadang tidak adil."
        val lat = -123.4212
        val lon = 121.331

        `when`(postStoryUseCase(file, description, lat, lon)).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<RegisterResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            addNewStoryViewModel.postStoryState.toList(states)
        }

        addNewStoryViewModel.postStory(
            file, description, lat, lon
        )

        assert(states.last() is ResponseState.Error)
        assertEquals(
            dummyErrorMessage,
            (states.last() as ResponseState.Error).message
        )

        job.cancel()
    }
}