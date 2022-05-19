package com.estudos.signupsignin.signin.domain

import com.estudos.signupsignin.signin.data.SignInRepository
import com.estudos.signupsignin.signin.data.SignInRepositoryImpl
import io.reactivex.Completable

interface SignInInteractor {
    fun fetchLogin(): Completable
}

class SignInInteractorImpl(val repository: SignInRepository = SignInRepositoryImpl()) : SignInInteractor {

    override fun fetchLogin(): Completable {
        return repository.fetchLogin()
    }
}