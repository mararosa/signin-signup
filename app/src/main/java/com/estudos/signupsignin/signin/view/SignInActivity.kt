package com.estudos.signupsignin.signin.view

import GenericTextWatcher
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.estudos.signupsignin.success.view.SuccessActivity
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.signin.domain.SignInInteractorImpl
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.signin.viewmodel.SignInViewModelFactory
import com.estudos.signupsignin.signin.viewmodel.SignInViewState
import com.estudos.signupsignin.signup.view.SignUpActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel
    private val textWatcher = GenericTextWatcher()
    {
        sendTextFromInput()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SignInViewModelFactory(interactor = SignInInteractorImpl())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)

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
                    command.isButtonEnabled
                is SignInCommand.SendInvalidEmailMessage -> sendInvalidEmailMessage(getString(command.errorMessageRes))
                is SignInCommand.OpenSignUpScreen -> startActivity(SignUpActivity.intent(this))
            }
        })
        viewModel.viewStateLiveData.observe(this, Observer { state ->
            when (state) {
                is SignInViewState.Success -> {
                    binding.loadingView.isVisible = false
                    startActivity(Intent(this, SuccessActivity::class.java))
                }
                is SignInViewState.Error -> showError()
                is SignInViewState.Loading -> showLoading()
            }
        })
    }

    private fun sendTextFromInput() {
        val isAValidEmail =
            android.util.Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString())
                .matches()
        val userPassword = binding.inputPassword.text.toString()

        viewModel.verifyInputValues(
            isValidInputtedEmail = isAValidEmail,
            userInputtedPassword = userPassword
        )
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

    private fun sendInvalidEmailMessage(errorMessage: String) {
        binding.signinButton.isEnabled = false
        binding.inputEmail.error = errorMessage
    }

    private fun setupInputValues() {
        binding.inputEmail.addTextChangedListener(textWatcher)
        binding.inputPassword.addTextChangedListener(textWatcher)
    }

    private fun setupClickListeners() {
        binding.signinButton.setOnClickListener { viewModel.onLoginClick() }
        binding.signupButton.setOnClickListener { viewModel.onRegisterClick() }
    }


    companion object {
        fun intent(context: Context) = Intent(context, SignInActivity::class.java)
    }


}
