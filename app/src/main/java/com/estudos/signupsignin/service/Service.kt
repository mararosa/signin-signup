package com.estudos.signupsignin.service

import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Completable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    @GET("/login")
    fun fetchLogin(): Completable

    @POST("register")
    fun sendUserInfo(@Body request: RegisterUserInfoRequest): Completable
}

class ServiceImpl {
    companion object {
        private val BASE_URL = "https://edimara.free.beeceptor.com"
        private val clientBuilder = OkHttpClient.Builder()

        fun createService(): Service {
            val moshi =
                Moshi
                    .Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Service::class.java)
        }
    }
}