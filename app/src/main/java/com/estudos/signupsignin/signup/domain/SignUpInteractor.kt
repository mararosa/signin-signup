package com.estudos.signupsignin.signup.domain

import com.estudos.signupsignin.signin.data.SignInRepository
import com.estudos.signupsignin.signup.data.SignUpRepository
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.reactivex.Completable

interface SignUpInteractor {
    fun sendUserInfo(request: RegisterUserInfoRequest): Completable
}

class SignUpInteractorImpl(val repository: SignUpRepository = SignUpRepository()) : SignUpInteractor {

    override fun sendUserInfo(request: RegisterUserInfoRequest): Completable =
        repository.sendUserInfo(request)

}