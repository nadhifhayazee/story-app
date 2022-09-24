package com.nadhif.hayazee.network

import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("register")
    suspend fun register(

    )

    @GET("stories")
    suspend fun getStories(
        @Query("size") size: Int?,
        @Query("page") page: Int?,
        @Query("location") location: Int?
    )
}