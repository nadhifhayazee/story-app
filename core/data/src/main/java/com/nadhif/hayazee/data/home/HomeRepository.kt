package com.nadhif.hayazee.data.home

import com.nadhif.hayazee.model.response.GetStoryResponse
import retrofit2.Response

interface HomeRepository {
    suspend fun getStories(
        size: Int?,
        page: Int?,
        location: Int?,
    ): Response<GetStoryResponse>
}