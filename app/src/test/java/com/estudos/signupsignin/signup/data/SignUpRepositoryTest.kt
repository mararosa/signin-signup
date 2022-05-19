package com.estudos.signupsignin.signup.data

import com.estudos.signupsignin.service.Service
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class SignUpRepositoryTest {

    private lateinit var repository: SignUpRepository

    @RelaxedMockK
    private lateinit var service: Service

    private val request = mockk<RegisterUserInfoRequest>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = SignUpRepositoryImpl(service)
    }

    @Test
    fun `given service when call sendUserInfo then return success`() {
        // given
        every { service.fetchLogin() } returns Completable.complete()

        // when
        repository.sendUserInfo(request)
            //Then
            .test()
            .assertComplete()
    }


    @Test
    fun `given service when call sendUserInfo then return error`() {
        // given
        every { service.fetchLogin() } returns Completable.error(RuntimeException())

        // when
        repository.sendUserInfo(request)
            .test()
            .assertError { it is RuntimeException }
    }

}