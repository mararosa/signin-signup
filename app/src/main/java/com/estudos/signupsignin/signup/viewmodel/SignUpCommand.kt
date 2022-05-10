package com.estudos.signupsignin.signup.viewmodel

import androidx.annotation.StringRes
import com.estudos.signupsignin.signin.viewmodel.SignInCommand

sealed class SignUpCommand {

    data class ChangeButtonState(val isButtonEnabled: Boolean) : SignUpCommand()
    data class SendInvalidEmailMessage(@StringRes val errorMessageRes: Int) : SignUpCommand()
}
