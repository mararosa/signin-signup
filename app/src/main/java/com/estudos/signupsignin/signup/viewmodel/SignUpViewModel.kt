package com.estudos.signupsignin.signup.viewmodel

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import com.estudos.signupsignin.signup.domain.SignUpInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class SignUpViewModel(
    private val interactor: SignUpInteractor,
    private val scheduler: Scheduler,
    private val androidScheduler: Scheduler
) : ViewModel() {
    private val disposable = CompositeDisposable()

    val viewStateLiveData = MutableLiveData<SignUpViewState>()
    val commandLiveData = SingleLiveEvent<SignUpCommand>()

    fun verifyInputValues(userValues: SignUpValues) {
        val isEmptyFields = verifyEmptyFields(userValues)
        val isAValidEmail = verifiyEmail(userValues.isAValidEmail)
        val isAValidPassword = verifyPassword(
            userPassword = userValues.password,
            userConfirmPassword = userValues.confirmPassword
        )
        commandLiveData.value =
            SignUpCommand.ChangeButtonState(isEmptyFields && isAValidEmail && isAValidPassword)
    }

    private fun verifyEmptyFields(values: SignUpValues): Boolean {
        return values.name.isNotEmpty() && values.lastName.isNotEmpty() && values.email.isNotEmpty() && values.phoneNumber.isNotEmpty() && values.password.isNotEmpty() && values.confirmPassword.isNotEmpty()
    }

    private fun verifiyEmail(isAValidEmail: Boolean): Boolean {
        return if (isAValidEmail) {
            true
        } else {
            commandLiveData.value =
                SignUpCommand.SendInvalidEmailMessage(R.string.generic_email_error)
            false
        }
    }

    private fun verifyPassword(userPassword: String, userConfirmPassword: String): Boolean {
        return (userPassword.length >= MINIMUM_PASSOWORD_LENGTH && userPassword == userConfirmPassword)
    }

    fun onRegisterClick(request: RegisterUserInfoRequest) {
        disposable.add(
            interactor.sendUserInfo(request)
                .subscribeOn(scheduler)
                .observeOn(androidScheduler)
                .doOnSubscribe { viewStateLiveData.value = SignUpViewState.Loading }
                .subscribe(
                    {
                        viewStateLiveData.value = SignUpViewState.Success
                    },
                    {
                        viewStateLiveData.value = SignUpViewState.Error
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private companion object {
        private const val MINIMUM_PASSOWORD_LENGTH = 8
    }

}