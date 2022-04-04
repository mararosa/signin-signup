package com.estudos.signupsignin.signin.domain

import com.estudos.signupsignin.signin.data.SignInRepository
import io.reactivex.Completable

interface SignInInteractor {
    fun fetchLogin(email: String, password: String): Completable
}

class SignInInteractorImpl(val repository: SignInRepository = SignInRepository()) : SignInInteractor {

    override fun fetchLogin(email: String, password: String): Completable {
        return repository.fetchLogin()
    }
}