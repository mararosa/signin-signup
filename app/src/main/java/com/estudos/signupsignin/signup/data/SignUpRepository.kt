package com.estudos.signupsignin.signup.data

import com.estudos.signupsignin.service.NetworkModule
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.reactivex.Completable


class SignUpRepository() {
    val service = NetworkModule.createService()

    fun sendUserInfo(request: RegisterUserInfoRequest): Completable =
        service.sendUserInfo(request)
}