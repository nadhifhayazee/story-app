package com.nadhif.hayazee.data.auth

import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.LoginResponse
import com.nadhif.hayazee.model.response.RegisterResponse
import retrofit2.Response

class MockSuccessAuthServiceRepository : AuthRepository {
    override suspend fun register(body: RegisterRequest): Response<RegisterResponse> {
        val mockResponseBody = AuthDummies.getSuccessRegisterDummy()
        return Response.success(mockResponseBody)
    }

    override suspend fun login(body: LoginRequest): Response<LoginResponse> {
        val mockResponseBody = AuthDummies.getSuccessLoginDummy()
        return Response.success(mockResponseBody)
    }
}