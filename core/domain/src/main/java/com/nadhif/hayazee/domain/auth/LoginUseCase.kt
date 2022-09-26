package com.nadhif.hayazee.domain.auth

import com.nadhif.hayazee.data.auth.AuthRepository
import com.nadhif.hayazee.domain.BaseUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.response.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) : BaseUseCase() {

    operator fun invoke(body: LoginRequest): Flow<ResponseState<LoginResponse>> {
        return flow {
            emit(ResponseState.Loading())
            try {
                val response = authRepository.login(body)
                validateResponse(response,
                    onSuccess = {
                        emit(ResponseState.Success(it))
                    },
                    onError = {
                        emit(ResponseState.Error(it))
                    }
                )
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message ?: "Terjadi kesalahan"))
            }
        }
    }

}