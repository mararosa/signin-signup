package com.estudos.signupsignin.signup.viewmodel

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import com.estudos.signupsignin.signup.domain.SignUpInteractor
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpViewModelTest {
    private lateinit var viewModel: SignUpViewModel

    // Run tasks synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var interactor: SignUpInteractor

    private val scheduler = Schedulers.trampoline()

    @RelaxedMockK
    private lateinit var viewStateObserver: Observer<SignUpViewState>

    @RelaxedMockK
    private lateinit var commandObserver: Observer<SignUpCommand>

    @MockK
    private lateinit var resources: Resources

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        clearAllMocks()
        viewModel = SignUpViewModel(interactor, scheduler, scheduler)

        viewModel.viewStateLiveData.observeForever(viewStateObserver)
        viewModel.commandLiveData.observeForever(commandObserver)
    }

    @Test
    fun `given interactor when calling onRegisterClick then send success state`() {
        val request = REGISTER_REQUEST
        every { interactor.sendUserInfo(request) } returns Completable.complete()

        viewModel.onRegisterClick(request)

        viewStateObserver emitted SignUpViewState.Loading
        viewStateObserver emitted SignUpViewState.Success
    }

    @Test
    fun `given interactor when calling onRegisterClick then send error state`() {
        val request = REGISTER_REQUEST
        every { interactor.sendUserInfo(request) } returns Completable.error(RuntimeException())

        viewModel.onRegisterClick(request)

        viewStateObserver emitted SignUpViewState.Loading
        viewStateObserver emitted SignUpViewState.Error
    }

    @Test
    fun `given correct user values when calling verifyInputValues then send command true`() {
        val values = USER_INFORMATION

        viewModel.verifyInputValues(values)

        commandObserver emitted SignUpCommand.ChangeButtonState(true)
    }

    @Test
    fun `given invalid email when calling verifyInputValues then send command false`() {
        every { resources.getString(R.string.generic_email_error) } returns "invalid email"
        val values = USER_INFORMATION.copy(isAValidEmail = false)

        viewModel.verifyInputValues(values)

        commandObserver emitted SignUpCommand.ChangeButtonState(false)
        commandObserver emitted SignUpCommand.SendInvalidEmailMessage(R.string.generic_email_error)
    }

    @Test
    fun `given a password with less than 8 characters when calling verifyInputValues then send command false`() {
        val values = USER_INFORMATION.copy(password = "123456")

        viewModel.verifyInputValues(values)

        commandObserver emitted SignUpCommand.ChangeButtonState(false)
    }

    @Test
    fun `given two different passwords when calling verifyInputValues then send command false`() {
        val values = USER_INFORMATION.copy(password = "123456", confirmPassword = "abcdefgh")

        viewModel.verifyInputValues(values)

        commandObserver emitted SignUpCommand.ChangeButtonState(false)
    }

    @Test
    fun `given any empty value when calling verifyInputValues then send command false`() {
        val values = USER_INFORMATION.copy(name = "")

        viewModel.verifyInputValues(values)

        commandObserver emitted SignUpCommand.ChangeButtonState(false)
    }



    infix fun <T> Observer<T>.emitted(value: T) {
        verify { this@emitted.onChanged(value) }
    }

    companion object {
        private val REGISTER_REQUEST = RegisterUserInfoRequest(
            name = "mara",
            lastName = "rosa",
            phoneNumber = "+5511977433594",
            email = "mara@test.com",
            password = "123456789"
        )

        private val USER_INFORMATION = SignUpValues(
            name = "mara",
            lastName = "rosa",
            phoneNumber = "+5511988745874",
            email = "email@test.com",
            password = "android123",
            confirmPassword = "android123",
            isAValidEmail = true
        )
    }

}


