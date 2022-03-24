package com.estudos.signupsignin.signin.viewmodel

sealed class SignInCommand {

    data class ChangeButtonState(val values: Boolean) : SignInCommand()
    object OpenSignUpScreen : SignInCommand()
    data class SendInvalidEmailMessage(val errorMessage: String) : SignInCommand()
}
