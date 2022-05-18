package com.estudos.signupsignin.signin.data

import com.estudos.signupsignin.service.NetworkModule
import com.estudos.signupsignin.service.Service
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Completable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SignInRepository() {
    val service = NetworkModule.createService()

    fun fetchLogin(): Completable =
        service.fetchLogin()
}