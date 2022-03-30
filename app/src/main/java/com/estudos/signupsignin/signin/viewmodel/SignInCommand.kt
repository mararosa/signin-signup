package com.estudos.signupsignin.signin.viewmodel

import androidx.annotation.StringRes

sealed class SignInCommand {

    data class ChangeButtonState(val values: Boolean) : SignInCommand()
    object OpenSignUpScreen : SignInCommand()
    data class SendInvalidEmailMessage(@StringRes val errorMessageRes: Int) : SignInCommand()
}
