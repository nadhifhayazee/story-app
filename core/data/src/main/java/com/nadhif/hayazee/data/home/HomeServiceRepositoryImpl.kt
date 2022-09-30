package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class HomeServiceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {
    override suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?
    ): Response<GetStoryResponse> {
        return apiService.getStories(size, page, location)
    }
}