package com.nadhif.hayazee.data.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.data.MainDispatcherRule
import com.nadhif.hayazee.model.dummies.HomeDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StoryServiceRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    lateinit var mockFileMultipart: MultipartBody.Part
    lateinit var mockDescription: RequestBody
    lateinit var mockLat: RequestBody
    lateinit var mockLon: RequestBody

    private val mockSuccessStoryRepository = MockSuccessStoryRepository()
    private val mockErrorStoryRepository = MockErrorStoryRepository()

    @Before
    fun setup() {
        mockFileMultipart = MultipartBody.Part.createFormData(
            "photo",
            "images/photo.jpg",
            "image/jpg".toRequestBody()
        )
        mockDescription = "sabar itu indah".toRequestBody()
        mockLat = "-131.313".toRequestBody()
        mockLon = "113.222".toRequestBody()
    }

    @Test
    fun `success get stories`() = runTest {
        val expectedResponseBody = HomeDummy.getDummyStoryResponse()
        val actualResponse = mockSuccessStoryRepository.getStories(10, 1, 1)

        assert(actualResponse.isSuccessful)
        assertNotNull(actualResponse.body())
        assertEquals(expectedResponseBody?.listStory?.size, actualResponse.body()?.listStory?.size)

    }

    @Test
    fun `success post story`() = runTest {
        val expectedResponseBody = HomeDummy.getDummyPostStoryResponse()
        val actualResponse = mockSuccessStoryRepository.postStory(
            mockFileMultipart,
            mockDescription,
            mockLat,
            mockLon
        )

        assert(actualResponse.isSuccessful)
        assertNotNull(actualResponse.body())
        assertEquals(expectedResponseBody?.message, actualResponse.body()?.message)
    }

    @Test
    fun `failed get stories`() = runTest {
        val actualResponse = mockErrorStoryRepository.getStories(10, 1, 1)

        assert(!actualResponse.isSuccessful)
        assertNull(actualResponse.body())
        assertEquals(
            HomeDummy.storyErrorDummy,
            actualResponse.errorBody()?.string()
        )
    }

    @Test
    fun `failed post story`() = runTest {
        val actualResponse = mockErrorStoryRepository.postStory(
            mockFileMultipart,
            mockDescription,
            mockLat,
            mockLon
        )

        assert(!actualResponse.isSuccessful)
        assertNull(actualResponse.body())
        assertEquals(HomeDummy.storyErrorDummy, actualResponse.errorBody()?.string())
    }
}