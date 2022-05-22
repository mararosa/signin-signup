package com.estudos.signupsignin.signin.data

import com.estudos.signupsignin.service.Service
import com.estudos.signupsignin.service.ServiceImpl.Companion.createService
import io.reactivex.Completable

interface SignInRepository {
    fun fetchLogin(): Completable
}

class SignInRepositoryImpl(val service: Service = createService()) : SignInRepository {

    override fun fetchLogin(): Completable =
        service.fetchLogin()
}