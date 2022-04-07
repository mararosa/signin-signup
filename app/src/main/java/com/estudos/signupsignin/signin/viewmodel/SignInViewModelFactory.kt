package com.estudos.signupsignin.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estudos.signupsignin.signin.domain.SignInInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SignInViewModelFactory(val interactor: SignInInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            SignInInteractor::class.java,
            Scheduler::class.java,
            Scheduler::class.java
        )
            .newInstance(interactor, Schedulers.io(), AndroidSchedulers.mainThread())
    }
}