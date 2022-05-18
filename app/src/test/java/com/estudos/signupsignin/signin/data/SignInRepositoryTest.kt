package com.estudos.signupsignin.signin.data

import com.estudos.signupsignin.service.Service
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class SignInRepositoryTest {

    private lateinit var repository: SignInRepository

    @RelaxedMockK
    private lateinit var service: Service

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = SignInRepository(service)
    }

    @Test
    fun givenService_whenCallingfetchLogin_thenReturnSuccess() {
        // given
        every { service.fetchLogin() } returns Completable.complete()

        // then
        repository.fetchLogin()
            .test()
            .assertComplete()
    }

    @Test
    fun givenService_whenCallingfetchLogin_thenReturnError() {
        // given
        every { service.fetchLogin() } returns Completable.error(RuntimeException())

        // then
        repository.fetchLogin()
            .test()
            .assertError { it is RuntimeException }
    }

}