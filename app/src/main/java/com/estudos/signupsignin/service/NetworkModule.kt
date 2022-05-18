package com.estudos.signupsignin.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class NetworkModule {

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