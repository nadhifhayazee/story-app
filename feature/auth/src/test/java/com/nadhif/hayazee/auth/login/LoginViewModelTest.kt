package com.nadhif.hayazee.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.auth.MainDispatcherRule
import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.domain.auth.LoginUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.response.LoginResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule

    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `when login success`() = runTest {

        val expectedResult = ResponseState.Success(AuthDummies.getSuccessLoginDummy())

        `when`(loginUseCase(LoginRequest("fulan@gmail.com", "123456"))).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<LoginResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            loginViewModel.loginState.toList(states)
        }

        loginViewModel.login("fulan@gmail.com", "123456")


        assert(states.last() is ResponseState.Success)
        assertNotNull((states.last() as ResponseState.Success).data?.loginResult?.token)


        job.cancel()
    }

    @Test
    fun `when login error`() = runTest {

        val expectedResult =
            ResponseState.Error<LoginResponse>(AuthDummies.getErrorLoginDummy()?.message)

        `when`(loginUseCase(LoginRequest("fulan@gmail.com", "1234526"))).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<LoginResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            loginViewModel.loginState.toList(states)
        }

        loginViewModel.login("fulan@gmail.com", "1234526")


        assert(states.last() is ResponseState.Error)
        assertEquals((states.last() as ResponseState.Error).message, expectedResult.message)


        job.cancel()
    }
}