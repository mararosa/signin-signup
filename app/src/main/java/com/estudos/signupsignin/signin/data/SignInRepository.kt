package com.estudos.signupsignin.signin.data

import com.estudos.signupsignin.service.SignInService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Completable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class SignInRepository(val service: SignInService = createService()) {

    fun fetchLogin(): Completable {
        return service.fetchLogin()
    }

    companion object {
        private val BASE_URL = "https://edimara.free.beeceptor.com"
        private val clientBuilder = OkHttpClient.Builder()

        fun createService(): SignInService {
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
                .create(SignInService::class.java)
        }
    }
}