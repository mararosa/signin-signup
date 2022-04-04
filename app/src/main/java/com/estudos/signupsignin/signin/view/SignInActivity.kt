package com.estudos.signupsignin.signin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.estudos.signupsignin.TesteActivity
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.signin.viewmodel.SignInViewState
import com.estudos.signupsignin.signup.view.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

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
                is SignInCommand.ChangeButtonState -> binding.signinButton.isEnabled =
                    command.values
                is SignInCommand.SendInvalidEmailMessage -> sendErrorMessage(getString(command.errorMessageRes))
                is SignInCommand.OpenSignUpScreen -> startActivity(SignUpActivity.intent(this))
            }
        })
        viewModel.viewStateLiveData.observe(this, Observer { state ->
            when (state) {
                is SignInViewState.Success -> {
                    binding.loadingView.isVisible = false
                    startActivity(Intent(this, TesteActivity::class.java))
                }
                is SignInViewState.Error -> showError()
                is SignInViewState.Loading -> showLoading()
            }
        })
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
        binding.errorView.errorContainer.isVisible = false
        binding.container.isVisible = false
    }

    private fun showError() {
        binding.errorView.errorContainer.isVisible = true
        binding.container.isVisible = false
        binding.loadingView.isVisible = false
    }

    private fun sendErrorMessage(errorMessage: String) {
        binding.signinButton.isEnabled = false
        binding.inputEmail.error = errorMessage
    }

    private fun setupInputValues() {
        binding.inputEmail.addTextChangedListener(textWatcher)
        binding.inputPassword.addTextChangedListener(textWatcher)
    }

    private fun setupClickListeners() {
        binding.signupButton.setOnClickListener { viewModel.onSignUpClick() }
        binding.signinButton.setOnClickListener {
            viewModel.onLoginClick(email, password)
        }
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
            email = binding.inputEmail.text.toString()
            password = binding.inputPassword.text.toString()
        }
    }

    companion object {
        fun intent(context: Context) = Intent(context, SignInActivity::class.java)
    }
}
