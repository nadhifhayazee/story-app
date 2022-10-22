package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface StoryRepository {
    suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?,
    ): Response<GetStoryResponse>

    suspend fun postStory(
        file: MultipartBody.Part,
        description: RequestBody
    ): Response<RegisterResponse>
}