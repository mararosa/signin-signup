package com.estudos.signupsignin.signin.viewmodel

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    private var isValidEmail: Boolean = false
    private var isValidPassword: Boolean = false

    val viewStateLiveData = MutableLiveData<SignInViewState>()
    val commandLiveData = SingleLiveEvent<SignInCommand>()

    fun verifyEmail(isValidInputtedEmail: Boolean) {
        if (isValidInputtedEmail) {
            isValidEmail = true
        } else {
            commandLiveData.value =
                SignInCommand.SendInvalidEmailMessage(errorMessage = "Invalid e-mail")
            isValidEmail = false
        }
    }

    fun verifyPassword(userInputtedPassword: String) {
        isValidPassword = userInputtedPassword.length >= 8
    }

    fun verifyInputValues(isValidInputtedEmail: Boolean, userInputtedPassword: String) {
        verifyEmail(isValidInputtedEmail)
        verifyPassword(userInputtedPassword)
        commandLiveData.value = SignInCommand.ChangeButtonState(isValidEmail && isValidPassword)
    }

    fun onSignUpclick() {
        commandLiveData.value = SignInCommand.OpenSignUpScreen
    }
}