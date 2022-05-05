package com.estudos.signupsignin.signup.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.databinding.ActivitySignUpBinding
import com.estudos.signupsignin.signin.view.SignInActivity
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.util.GenericTextWatcher

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel = SignUpActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    companion object {
        fun intent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

}
