package com.estudos.signupsignin.service

import io.reactivex.Completable
import retrofit2.http.GET

interface SignInService {

    @GET("/login")
    fun fetchLogin(): Completable
}