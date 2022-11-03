package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.dummies.HomeDummy
import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class MockErrorStoryRepository : StoryRepository {
    override suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?
    ): Response<GetStoryResponse> {
        val errorResponseBody =
            HomeDummy.storyErrorDummy.toResponseBody("application/json".toMediaTypeOrNull())
        return Response.error(400, errorResponseBody)
    }

    override suspend fun postStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Response<RegisterResponse> {
        val errorResponseBody =
            HomeDummy.storyErrorDummy.toResponseBody("application/json".toMediaTypeOrNull())
        return Response.error(400, errorResponseBody)
    }
}