package com.estudos.signupsignin.signin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignInViewModel: ViewModel() {

    val voLiveData = MutableLiveData<SignInVO>()
    val viewStateLiveData = MutableLiveData<SignInViewState>()
//    val commandLiveData = SingleLiveEvent<SignInCommand>()
}