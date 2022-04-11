package com.estudos.signupsignin.signup.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.estudos.signupsignin.databinding.ActivitySignUpBinding
import com.estudos.signupsignin.signup.viewmodel.SignUpViewModel
import com.estudos.signupsignin.signup.viewmodel.SignUpViewModelFactory

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SignUpViewModelFactory()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    companion object {
        fun intent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

}
