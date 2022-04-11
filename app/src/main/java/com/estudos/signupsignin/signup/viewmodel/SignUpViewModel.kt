package com.estudos.signupsignin.signup.viewmodel

import SingleLiveEvent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel(
) : ViewModel() {

    val viewStateLiveData = MutableLiveData<SignUpViewState>()
    val commandLiveData = SingleLiveEvent<SignUpCommand>()

}