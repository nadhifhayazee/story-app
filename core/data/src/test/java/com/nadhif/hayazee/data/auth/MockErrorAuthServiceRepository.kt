package com.nadhif.hayazee.data.auth

import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class MockErrorAuthServiceRepository : AuthRepository {
    override suspend fun register(body: RegisterRequest): Response<RegisterResponse> {
        val errorResponseBody =
            AuthDummies.registerErrorBody.toResponseBody("application/json".toMediaTypeOrNull())
        return Response.error(400, errorResponseBody)
    }

    override suspend fun login(body: LoginRequest): Response<LoginResponse> {
        val errorResponseBody =
            AuthDummies.loginErrorBody.toResponseBody("application/json".toMediaTypeOrNull())
        return Response.error(400, errorResponseBody)
    }
}