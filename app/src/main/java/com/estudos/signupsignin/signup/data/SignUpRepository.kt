package com.estudos.signupsignin.signup.data

import com.estudos.signupsignin.service.Service
import com.estudos.signupsignin.service.ServiceImpl
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.reactivex.Completable

interface SignUpRepository {
    fun sendUserInfo(request: RegisterUserInfoRequest): Completable
}

class SignUpRepositoryImpl(val service: Service = ServiceImpl.createService()) : SignUpRepository {

    override fun sendUserInfo(request: RegisterUserInfoRequest): Completable =
        service.sendUserInfo(request)
}