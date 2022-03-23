package com.estudos.signupsignin.signin.viewmodel

sealed class SignInCommand {

    object EnableLoginButton : SignInCommand()

    data class SendInvalidEmailMessage(val errorMessage: String) : SignInCommand()
}
