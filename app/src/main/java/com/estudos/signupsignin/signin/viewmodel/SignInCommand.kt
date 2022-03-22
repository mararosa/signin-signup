package com.estudos.signupsignin.signin.viewmodel

sealed class SignInCommand {

    data class SendInvalidEmailMessage(
        val errorMessage: String
    ) : SignInCommand()

    object EnableLoginButton : SignInCommand()
}
