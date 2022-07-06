package com.estudos.signupsignin.signup.viewmodel

data class SignUpValues(
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val isAValidEmail: Boolean,
)
