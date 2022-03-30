package com.estudos.signupsignin.signin.viewmodel

import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signin.domain.SignInInteractor
import com.estudos.signupsignin.signin.domain.SignInInteractorImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInViewModel : ViewModel() {
    private val interactor = SignInInteractorImpl()
    private val disposable = CompositeDisposable()

    private var isValidEmail: Boolean = false
    private var isValidPassword: Boolean = false

    val viewStateLiveData = MutableLiveData<SignInViewState>()
    val commandLiveData = SingleLiveEvent<SignInCommand>()


    fun verifyEmail(isValidInputtedEmail: Boolean) {
        if (isValidInputtedEmail) {
            isValidEmail = true
        } else {
            commandLiveData.value =
                SignInCommand.SendInvalidEmailMessage(errorMessage = R.string.sign_in_email_error)
            isValidEmail = false
        }
    }

    fun verifyPassword(userInputtedPassword: String) {
        isValidPassword = userInputtedPassword.length >= MINIMUM_PASSOWORD_LENGTH
    }

    fun verifyInputValues(isValidInputtedEmail: Boolean, userInputtedPassword: String) {
        verifyEmail(isValidInputtedEmail)
        verifyPassword(userInputtedPassword)
        commandLiveData.value = SignInCommand.ChangeButtonState(isValidEmail && isValidPassword)
    }

    fun onSignUpClick() {
        commandLiveData.value = SignInCommand.OpenSignUpScreen
    }

    fun onLoginClick(email: String, password: String) {
        disposable.add(
            interactor.fetchLogin(email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewStateLiveData.value = SignInViewState.Loading }
                .subscribe(
                    {
                        viewStateLiveData.value = SignInViewState.Success
                    },
                    {
                        viewStateLiveData.value = SignInViewState.Error
                        Log.e("ERROR", "SignInViewModel.fetchLogin", it)
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    private companion object {
        private const val MINIMUM_PASSOWORD_LENGTH = 8
    }
}