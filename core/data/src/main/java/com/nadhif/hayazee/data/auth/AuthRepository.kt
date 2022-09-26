package com.nadhif.hayazee.data.auth

import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import retrofit2.Response

interface AuthRepository {

    suspend fun register(body: RegisterRequest): Response<RegisterResponse>
    suspend fun login(body: LoginRequest): Response<LoginResponse>

}