package com.estudos.signupsignin.signup.viewmodel

sealed class SignUpCommand {

    object EnableLoginButton : SignUpCommand()

    data class SendInvalidEmailMessage(val errorMessage: String) : SignUpCommand()
}
