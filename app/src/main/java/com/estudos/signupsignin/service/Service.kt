package com.estudos.signupsignin.service

import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("/login")
    fun fetchLogin(): Completable

    @POST("register")
    fun sendUserInfo(@Body request: RegisterUserInfoRequest): Completable
}