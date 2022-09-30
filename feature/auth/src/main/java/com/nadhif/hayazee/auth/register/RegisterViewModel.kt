package com.nadhif.hayazee.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.domain.auth.RegisterUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.RegisterResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {


    private val _registerState = MutableSharedFlow<ResponseState<RegisterResponse>>()
    val registerState get() = _registerState.asSharedFlow()

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val body = RegisterRequest(
                name, email, password
            )
            registerUseCase(body).collectLatest {
                _registerState.emit(it)
            }
        }
    }

    class Factory @Inject constructor(
        registerUseCase: RegisterUseCase
    ) : BaseVmFactory(
        RegisterViewModel(registerUseCase)
    )

}