package com.estudos.signupsignin.signin.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel = SignInViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupview()
        watchEvents()
    }

    private fun watchEvents() {
        viewModel.commandLiveData.observe(this, Observer { command ->
            when (command) {
                is SignInCommand.EnableLoginButton -> binding.signinButton.isEnabled = true
                is SignInCommand.SendInvalidEmailMessage -> sendErrorMessage(command.errorMessage)
            }
        })
    }

    private fun sendErrorMessage(errorMessage: String) {
        binding.signinButton.isEnabled = false
        binding.inputEmail.error = errorMessage
    }

    private fun setupview() {
        binding.inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val userEmail =
                    android.util.Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString())
                        .matches()
                viewModel.verifyEmail(userEmail)
            }
        })
    }
}