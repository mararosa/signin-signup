package com.estudos.signupsignin.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estudos.signupsignin.signup.domain.SignUpInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignUpViewModelFactory(val interactor: SignUpInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SignUpInteractor::class.java,
            Scheduler::class.java,
            Scheduler::class.java
        )
            .newInstance(interactor, Schedulers.io(), AndroidSchedulers.mainThread())
    }
}