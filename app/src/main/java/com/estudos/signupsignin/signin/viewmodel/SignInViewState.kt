package com.estudos.signupsignin.signin.viewmodel

sealed class SignInViewState{
    object Success : SignInViewState()
    object Error : SignInViewState()
}
