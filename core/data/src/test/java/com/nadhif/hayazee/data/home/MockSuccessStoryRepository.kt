package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.model.dummies.HomeDummy
import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MockSuccessStoryRepository : StoryRepository {
    override suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?
    ): Response<GetStoryResponse> {
        return Response.success(HomeDummy.getDummyStoryResponse())
    }

    override suspend fun postStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Response<RegisterResponse> {
        return Response.success(HomeDummy.getDummyPostStoryResponse())
    }
}