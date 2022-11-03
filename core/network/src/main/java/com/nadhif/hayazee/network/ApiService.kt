package com.nadhif.hayazee.network

import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): Response<LoginResponse>

    @GET("stories")
    suspend fun getStories(
        @Query("size") size: Int?,
        @Query("page") page: Int?,
        @Query("location") location: Int?
    ): Response<GetStoryResponse>

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?
    ): Response<RegisterResponse>
}