package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import com.nadhif.hayazee.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

class StoryServiceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : StoryRepository {
    override suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?
    ): Response<GetStoryResponse> {
        return apiService.getStories(size, page, location)
    }

    override suspend fun postStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?
    ): Response<RegisterResponse> {
        return apiService.postStory(file, description, lat, lon)
    }
}