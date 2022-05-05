package com.estudos.signupsignin.signin.domain

import com.estudos.signupsignin.signin.data.SignInRepository
import io.reactivex.Completable

interface SignInInteractor {
    fun fetchLogin(): Completable
}

class SignInInteractorImpl(val repository: SignInRepository = SignInRepository()) : SignInInteractor {

    override fun fetchLogin(): Completable {
        return repository.fetchLogin()
    }
}