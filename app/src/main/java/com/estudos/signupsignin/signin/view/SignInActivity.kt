package com.estudos.signupsignin.signin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.signup.view.SignUpActivity
import com.estudos.signupsignin.util.GenericTextWatcher

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel = SignInViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        watchEvents()
        setupInputValues()
        setupClickListeners()
    }

    private fun watchEvents() {
        viewModel.commandLiveData.observe(this, Observer { command ->
            when (command) {
                is SignInCommand.EnableLoginButton -> binding.signinButton.isEnabled = true
                is SignInCommand.SendInvalidEmailMessage -> sendErrorMessage(command.errorMessage)
                is SignInCommand.OpenSignUpScreen -> startActivity(SignUpActivity.intent(this))
            }
        })
    }

    private fun sendErrorMessage(errorMessage: String) {
        binding.signinButton.isEnabled = false
        binding.inputEmail.error = errorMessage
    }

    private fun setupInputValues() {
        binding.inputEmail.addTextChangedListener(textWatcher)
        binding.inputEmail.addTextChangedListener(textWatcher)
    }

    private fun setupClickListeners() {
        binding.signupButton.setOnClickListener { viewModel.onSignUpclick() }
    }

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val userEmail =
                android.util.Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString())
                    .matches()
            viewModel.verifyInputValues(
                isValidInputtedEmail = userEmail,
                userInputtedPassword = binding.inputPassword.text.toString()
            )
        }
    }

    companion object {
        fun intent(context: Context) = Intent(context, SignInActivity::class.java)
    }
}
