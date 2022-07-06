package com.estudos.signupsignin.signup.view

import GenericTextWatcher
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.estudos.signupsignin.success.view.SuccessActivity
import com.estudos.signupsignin.databinding.ActivitySignUpBinding
import com.estudos.signupsignin.signup.data.request.RegisterUserInfoRequest
import com.estudos.signupsignin.signup.domain.SignUpInteractorImpl
import com.estudos.signupsignin.signup.viewmodel.SignUpCommand
import com.estudos.signupsignin.signup.viewmodel.SignUpValues
import com.estudos.signupsignin.signup.viewmodel.SignUpViewModel
import com.estudos.signupsignin.signup.viewmodel.SignUpViewModelFactory
import com.estudos.signupsignin.signup.viewmodel.SignUpViewState

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private val textWatcher = GenericTextWatcher()
    {
        sendTextFromInput()
    }
    private var userInformation: RegisterUserInfoRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = SignUpViewModelFactory(interactor = SignUpInteractorImpl())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel::class.java)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setupToolbar()
        watchEvents()
        setupInputValues()
        setupClickListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun watchEvents() {
        viewModel.commandLiveData.observe(this, Observer { command ->
            when (command) {
                is SignUpCommand.ChangeButtonState -> binding.signupButton.isEnabled =
                    command.isButtonEnabled
                is SignUpCommand.SendInvalidEmailMessage -> sendInvalidEmailMessage(
                    getString(
                        command.errorMessageRes
                    )
                )
            }
        })
        viewModel.viewStateLiveData.observe(this, Observer { state ->
            when (state) {
                is SignUpViewState.Loading -> showLoading()
                is SignUpViewState.Success -> setupView()
                is SignUpViewState.Error -> showError()
            }
        })
    }

    private fun setupView() {
        binding.loadingView.loadingContainer.isVisible = false
        startActivity(Intent(this, SuccessActivity::class.java))
    }

    private fun sendInvalidEmailMessage(message: String) {
        binding.signupButton.isEnabled = false
        binding.inputEmail.error = message
    }

    private fun sendTextFromInput() {
        val isAValidEmail =
            android.util.Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.text.toString())
                .matches()
        with(binding) {
            viewModel.verifyInputValues(
                SignUpValues(
                    name = inputName.text.toString(),
                    lastName = inputLastName.text.toString(),
                    phoneNumber = inputPhone.text.toString(),
                    email = inputEmail.text.toString(),
                    password = inputPassword.text.toString(),
                    confirmPassword = inputConfirmPassword.text.toString(),
                    isAValidEmail = isAValidEmail
                )
            )
        }
    }

    private fun setupInputValues() {
        with(binding) {
            inputName.addTextChangedListener(textWatcher)
            inputLastName.addTextChangedListener(textWatcher)
            inputPhone.addTextChangedListener(textWatcher)
            inputEmail.addTextChangedListener(textWatcher)
            inputPassword.addTextChangedListener(textWatcher)
            inputConfirmPassword.addTextChangedListener(textWatcher)
        }
    }

    private fun setupClickListeners() {
        binding.signupButton.setOnClickListener {
            with(binding) {
                viewModel.onRegisterClick(
                    request = RegisterUserInfoRequest(
                        inputName.text.toString(),
                        inputLastName.text.toString(),
                        inputPhone.text.toString(),
                        inputEmail.text.toString(),
                        inputPassword.text.toString()
                    )
                )
            }
        }
    }

    private fun showLoading() {
        binding.loadingView.loadingContainer.isVisible = true
        binding.errorView.errorContainer.isVisible = false
        binding.container.isVisible = false
    }

    private fun showError() {
        binding.errorView.errorContainer.isVisible = true
        binding.container.isVisible = false
        binding.loadingView.loadingContainer.isVisible = false
    }

    companion object {
        fun intent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

}
