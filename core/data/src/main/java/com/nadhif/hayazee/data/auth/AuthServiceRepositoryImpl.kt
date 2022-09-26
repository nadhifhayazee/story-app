package com.nadhif.hayazee.data.auth

import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import com.nadhif.hayazee.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class AuthServiceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun register(body: RegisterRequest): Response<RegisterResponse> {
        return apiService.register(body)
    }

    override suspend fun login(body: LoginRequest): Response<LoginResponse> {
        return apiService.login(body)
    }
}