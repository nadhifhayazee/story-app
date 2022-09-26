package com.nadhif.hayazee.domain

import com.nadhif.hayazee.model.BaseServiceResponse
import retrofit2.Response

open class BaseUseCase {

    suspend fun <T: BaseServiceResponse> validateResponse(
        response: Response<T>,
        onSuccess: suspend (data: T) -> Unit,
        onError: suspend (message: String) -> Unit
    ) {
        if (response.isSuccessful) {
            if (response.body()?.error == false) {
                onSuccess(response.body() as T)
            } else {
                onError(response.body()?.message ?: "")
            }
        } else {
            onError("Terjadi Kesalahan")
        }
    }
}