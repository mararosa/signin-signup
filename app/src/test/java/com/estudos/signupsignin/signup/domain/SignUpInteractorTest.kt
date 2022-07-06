package com.estudos.signupsignin.signup.domain

import com.estudos.signupsignin.signup.data.SignUpRepository
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class SignUpInteractorTest {

    private lateinit var interactor: SignUpInteractor

    @RelaxedMockK
    private lateinit var repository: SignUpRepository

    private val request = mockk<RegisterUserInfoRequest>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        interactor = SignUpInteractorImpl(repository)
    }

    @Test
    fun `given repository when calling sendUserInfo then return success`() {
        // given
        every { repository.sendUserInfo(request) } returns Completable.complete()

        // then
        interactor.sendUserInfo(request)
            .test()
            .assertComplete()
    }

    @Test
    fun givenRepository_whenCallingfetchLogin_thenReturnError() {
        // given
        every { repository.sendUserInfo(request) } returns Completable.error(RuntimeException())

        // then
        interactor.sendUserInfo(request)
            .test()
            .assertError { it is RuntimeException }
    }

}