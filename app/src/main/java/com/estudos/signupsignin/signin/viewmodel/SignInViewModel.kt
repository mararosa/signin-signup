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
                SignInCommand.SendInvalidEmailMessage(errorMessage = "Invalid E-mail")
        }
    }

    fun verifyPassword(userInputtedPassword: String) {
        if (userInputtedPassword.length >= 8) isValidPassword = true
    }

    fun verifyInputValues(isValidInputtedEmail: Boolean, userInputtedPassword: String) {
        verifyEmail(isValidInputtedEmail)
        verifyPassword(userInputtedPassword)
        if (isValidEmail && isValidPassword) commandLiveData.value = SignInCommand.EnableLoginButton
    }
}