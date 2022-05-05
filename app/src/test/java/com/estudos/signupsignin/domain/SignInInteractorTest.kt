package com.estudos.signupsignin.domain

import com.estudos.signupsignin.signin.data.SignInRepository
import com.estudos.signupsignin.signin.domain.SignInInteractor
import com.estudos.signupsignin.signin.domain.SignInInteractorImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class SignInInteractorTest {

    private lateinit var interactor: SignInInteractor

    @RelaxedMockK
    private lateinit var repository: SignInRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        interactor = SignInInteractorImpl(repository)
    }

    @Test
    fun givenRepository_whenCallingfetchLogin_thenReturnSuccess() {
        // given
        every { repository.fetchLogin() } returns Completable.complete()

        // then
        interactor.fetchLogin()
            .test()
            .assertComplete()
    }

    @Test
    fun givenRepository_whenCallingfetchLogin_thenReturnError() {
        // given
        every { repository.fetchLogin() } returns Completable.error(RuntimeException())

        // then
        interactor.fetchLogin()
            .test()
            .assertError { it is RuntimeException }
    }

}