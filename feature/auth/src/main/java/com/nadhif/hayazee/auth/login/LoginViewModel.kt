package com.nadhif.hayazee.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhif.hayazee.baseview.viewmodel.BaseVmFactory
import com.nadhif.hayazee.domain.auth.LoginUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.response.LoginResponse
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginState = MutableSharedFlow<ResponseState<LoginResponse>>()
    val loginState get() = _loginState.asSharedFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            val body = LoginRequest(email, password)
            loginUseCase(body).collectLatest {
                _loginState.emit(it)
            }
        }
    }

    class Factory @Inject constructor(
        loginUseCase: LoginUseCase
    ) : BaseVmFactory(
        LoginViewModel(loginUseCase)
    )
}