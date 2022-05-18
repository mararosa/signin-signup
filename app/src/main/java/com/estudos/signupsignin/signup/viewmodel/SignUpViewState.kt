package com.estudos.signupsignin.signup.viewmodel

import com.estudos.signupsignin.signin.viewmodel.SignInViewState

sealed class SignUpViewState {

    object Success : SignUpViewState()
    object Error : SignUpViewState()
    object Loading : SignUpViewState()
}
