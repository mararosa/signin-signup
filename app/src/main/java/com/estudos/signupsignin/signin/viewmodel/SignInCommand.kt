package com.estudos.signupsignin.signin.viewmodel

sealed class SignInCommand {

    object EnableLoginButton : SignInCommand()
    object OpenSignUpScreen : SignInCommand()
    data class SendInvalidEmailMessage(val errorMessage: String) : SignInCommand()
}
