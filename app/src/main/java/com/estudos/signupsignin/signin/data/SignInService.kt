package com.estudos.signupsignin.signin.data

import io.reactivex.Completable
import retrofit2.http.GET

interface SignInService {

    @GET("/login")
    fun fetchLogin(): Completable
}