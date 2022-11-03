package com.nadhif.hayazee.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.auth.MainDispatcherRule
import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.domain.auth.RegisterUseCase
import com.nadhif.hayazee.model.common.ResponseState
import com.nadhif.hayazee.model.request.RegisterRequest
import com.nadhif.hayazee.model.response.RegisterResponse
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
class RegisterViewModelTest {

    @get:Rule

    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainDispatcherRule()

    @Mock
    private lateinit var registerUseCase: RegisterUseCase

    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(registerUseCase)
    }

    @Test
    fun `when register success`() = runTest {

        val expectedResult = ResponseState.Success(AuthDummies.getSuccessRegisterDummy())

        `when`(registerUseCase(RegisterRequest("mrfulan", "mrfulan@gmail.com", "123456"))).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<RegisterResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            registerViewModel.registerState.toList(states)
        }

        registerViewModel.register("mrfulan", "mrfulan@gmail.com", "123456")


        assert(states.last() is ResponseState.Success)
        assert((states.last() as ResponseState.Success).data?.error == false)
        assertEquals(
            (states.last() as ResponseState.Success).data?.message,
            expectedResult.data?.message
        )

        job.cancel()
    }

    @Test
    fun `when register error`() = runTest {

        val expectedResult =
            ResponseState.Error<RegisterResponse>(AuthDummies.getErrorRegisterDummy()?.message)

        `when`(registerUseCase(RegisterRequest("mrfulan", "fulan@gmail.com", "1234526"))).then {
            flow {
                emit(expectedResult)
            }
        }

        val states = mutableListOf<ResponseState<RegisterResponse>>()
        val job = launch(UnconfinedTestDispatcher()) {
            registerViewModel.registerState.toList(states)
        }

        registerViewModel.register("mrfulan", "fulan@gmail.com", "1234526")


        assert(states.last() is ResponseState.Error)
        assertEquals((states.last() as ResponseState.Error).message, expectedResult.message)


        job.cancel()
    }
}