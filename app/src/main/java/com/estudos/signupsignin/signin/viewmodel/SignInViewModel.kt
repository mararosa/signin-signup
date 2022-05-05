package com.estudos.signupsignin.signin.viewmodel

import SingleLiveEvent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estudos.signupsignin.R
import com.estudos.signupsignin.signin.domain.SignInInteractor
import com.estudos.signupsignin.signin.domain.SignInInteractorImpl
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignInViewModel(
    private val interactor: SignInInteractor,
    private val scheduler: Scheduler,
    private val androidScheduler: Scheduler
) : ViewModel() {
    private val disposable = CompositeDisposable()

    val viewStateLiveData = MutableLiveData<SignInViewState>()
    val commandLiveData = SingleLiveEvent<SignInCommand>()


    private fun verifyEmail(isValidInputtedEmail: Boolean): Boolean {
        return if (isValidInputtedEmail) {
            true
        } else {
            commandLiveData.value =
                SignInCommand.SendInvalidEmailMessage(errorMessageRes = R.string.sign_in_email_error)
            false
        }
    }

    private fun verifyPassword(userInputtedPassword: String): Boolean {
       return userInputtedPassword.length >= MINIMUM_PASSOWORD_LENGTH
    }

    fun verifyInputValues(isValidInputtedEmail: Boolean, userInputtedPassword: String) {
        val isValidEmail = verifyEmail(isValidInputtedEmail)
        val isValidPassword = verifyPassword(userInputtedPassword)
        commandLiveData.value = SignInCommand.ChangeButtonState(isValidEmail && isValidPassword)
    }

    fun onSignUpClick() {
        commandLiveData.value = SignInCommand.OpenSignUpScreen
    }

    fun onLoginClick() {
        disposable.add(
            interactor.fetchLogin()
                .subscribeOn(scheduler)
                .observeOn(androidScheduler)
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