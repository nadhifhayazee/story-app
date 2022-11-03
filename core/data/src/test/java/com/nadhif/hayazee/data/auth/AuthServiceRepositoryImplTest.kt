package com.nadhif.hayazee.data.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nadhif.hayazee.data.MainDispatcherRule
import com.nadhif.hayazee.model.dummies.AuthDummies
import com.nadhif.hayazee.model.request.LoginRequest
import com.nadhif.hayazee.model.request.RegisterRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthServiceRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val mockSuccessAuthServiceRepository = MockSuccessAuthServiceRepository()
    private val mockErrorAuthServiceRepository = MockErrorAuthServiceRepository()

    @Test
    fun `login success`() = runTest {
        val expectedResponseBody = AuthDummies.getSuccessLoginDummy()
        val actualResponse =
            mockSuccessAuthServiceRepository.login(LoginRequest("fulan@gmail.com", "123456"))

        assert(actualResponse.isSuccessful)
        assertNotNull(actualResponse.body())
        assertEquals(
            expectedResponseBody?.loginResult?.token,
            actualResponse.body()?.loginResult?.token
        )
    }

    @Test
    fun `register success`() = runTest {
        val expectedResponseBody = AuthDummies.getSuccessRegisterDummy()
        val actualResponse =
            mockSuccessAuthServiceRepository.register(
                RegisterRequest(
                    "Mrfulan ",
                    "fulan@gmail.com",
                    "123456"
                )
            )

        assert(actualResponse.isSuccessful)
        assertNotNull(actualResponse.body())
        assertEquals(
            expectedResponseBody?.message,
            actualResponse.body()?.message
        )
    }

    @Test
    fun `login error`() = runTest {
        val actualResponse =
            mockErrorAuthServiceRepository.login(LoginRequest("fulan@gmail.com", "123456"))

        assert(!actualResponse.isSuccessful)
        assertNull(actualResponse.body())
        assertEquals(AuthDummies.loginErrorBody, actualResponse.errorBody()?.string())
    }

    @Test
    fun `register error`() = runTest {
        val actualResponse =
            mockErrorAuthServiceRepository.register(
                RegisterRequest(
                    "Mrfulan ",
                    "fulan@gmail.com",
                    "123456"
                )
            )

        assert(!actualResponse.isSuccessful)
        assertNull(actualResponse.body())
        assertEquals(AuthDummies.registerErrorBody, actualResponse.errorBody()?.string())
    }
}