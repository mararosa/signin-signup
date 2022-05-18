package com.estudos.signupsignin.signup.data.request

import com.squareup.moshi.Json

data class RegisterUserInfoRequest(
    @Json(name = "user_name") val name: String,
    @Json(name = "user_last_name") val lastName: String,
    @Json(name = "user_phone_number") val phoneNumber: String,
    @Json(name = "user_email") val email: String,
    @Json(name = "user_password") val password: String
)
