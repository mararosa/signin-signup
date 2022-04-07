package com.estudos.signupsignin.viewmodel

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signin.domain.SignInInteractor
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.signin.viewmodel.SignInViewState
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
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
        val isValidEmail = true
        val isValidPassword = true

        // when
        viewModel.verifyInputValues(email, password)

        // then
        commandObserver emitted SignInCommand.ChangeButtonState(isValidEmail && isValidPassword)
    }

    @Test
    fun givenInputValues_whenEmailIsInvalid_thenSendCommandFalseAndMessageError() {
        // given
        val email = false
        val password = "12345678"
        val isValidEmail = false
        val isValidPassword = true

        // when
        viewModel.verifyInputValues(email, password)

        // then
        commandObserver emitted SignInCommand.SendInvalidEmailMessage(R.string.sign_in_email_error)
        commandObserver emitted SignInCommand.ChangeButtonState(isValidEmail && isValidPassword)

    }

    @Test
    fun givenInteractor_whenClickOnButton_thenSendSuccessState() {
        // given
        val email = "mara_rosa@teste.com"
        val password = "123456789"
        every { interactor.fetchLogin(email, password) } returns Completable.complete()

        // when
        viewModel.onLoginClick(email, password)

        // then
        viewStateObserver emitted SignInViewState.Loading
        viewStateObserver emitted SignInViewState.Success
    }

    infix fun <T> Observer<T>.emitted(value: T) {
        verify { this@emitted.onChanged(value) }
    }

}


