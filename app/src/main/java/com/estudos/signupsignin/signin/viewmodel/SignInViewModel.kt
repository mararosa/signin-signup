package com.estudos.signupsignin.signin.viewmodel

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel : ViewModel() {

    val viewStateLiveData = MutableLiveData<SignInViewState>()
    val commandLiveData = SingleLiveEvent<SignInCommand>()

    fun verifyEmail(isValidEmail: Boolean) {
        if (isValidEmail) {
            commandLiveData.value = SignInCommand.EnableLoginButton
        } else {
            commandLiveData.value =
                SignInCommand.SendInvalidEmailMessage(errorMessage = "Invalid E-mail")
        }
    }
}