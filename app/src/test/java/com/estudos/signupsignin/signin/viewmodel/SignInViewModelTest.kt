package com.estudos.signupsignin.signin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signin.domain.SignInInteractor
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignInViewModelTest {
    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SignInViewModel

    @RelaxedMockK
    private lateinit var interactor: SignInInteractor

    private val scheduler = Schedulers.trampoline()

    @RelaxedMockK
    private lateinit var viewStateObserver: Observer<SignInViewState>

    @RelaxedMockK
    private lateinit var commandObserver: Observer<SignInCommand>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        clearAllMocks()
        viewModel = SignInViewModel(interactor, scheduler, scheduler)

        viewModel.viewStateLiveData.observeForever(viewStateObserver)
        viewModel.commandLiveData.observeForever(commandObserver)
    }

    @Test
    fun givenInputValues_whenEmailAndPasswordIsValid_thenSendCommandTrue() {
        // given
        val email = true
        val password = "12345678"

        // when
        viewModel.verifyInputValues(email, password)

        // then
        commandObserver emitted SignInCommand.ChangeButtonState(true)
    }

    @Test
    fun givenInputValues_whenPasswordIsInvalid_thenSendCommandFalse() {
        // given
        val email = true
        val password = "123"

        // when
        viewModel.verifyInputValues(email, password)

        // then
        commandObserver emitted SignInCommand.ChangeButtonState(false)
    }

    @Test
    fun givenInputValues_whenEmailIsInvalid_thenSendCommandFalseAndMessageError() {
        // given
        val email = false
        val password = "12345678"

        // when
        viewModel.verifyInputValues(email, password)

        // then
        commandObserver emitted SignInCommand.SendInvalidEmailMessage(R.string.sign_in_email_error)
        commandObserver emitted SignInCommand.ChangeButtonState(false)

    }

    @Test
    fun givenInteractor_whenClickOnButton_thenSendSuccessState() {
        // given
        val email = "mara_rosa@teste.com"
        val password = "123456789"
        every { interactor.fetchLogin() } returns Completable.complete()

        // when
        viewModel.onLoginClick()

        // then
        viewStateObserver emitted SignInViewState.Loading
        viewStateObserver emitted SignInViewState.Success
    }

    infix fun <T> Observer<T>.emitted(value: T) {
        verify { this@emitted.onChanged(value) }
    }

}


