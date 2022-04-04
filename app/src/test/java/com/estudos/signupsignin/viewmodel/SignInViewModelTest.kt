package com.estudos.signupsignin.viewmodel

import androidx.lifecycle.Observer
import com.estudos.signupsignin.signin.domain.SignInInteractor
import com.estudos.signupsignin.signin.viewmodel.SignInCommand
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel
import com.estudos.signupsignin.signin.viewmodel.SignInViewState
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before

class SignInViewModelTest {

    private lateinit var viewModel: SignInViewModel

    @RelaxedMockK
    private lateinit var interactor: SignInInteractor

    @RelaxedMockK
    private lateinit var disposable: CompositeDisposable

    @RelaxedMockK
    private lateinit var viewStateObserver: Observer<SignInViewState>

    @RelaxedMockK
    private lateinit var commandObserver: Observer<SignInCommand>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        clearAllMocks()
        viewModel = SignInViewModel(interactor)

        viewModel.viewStateLiveData.observeForever(viewStateObserver)
        viewModel.commandLiveData.observeForever(commandObserver)
    }

//    @Test
//    fun givenInputValues_whenEmailAndPasswordIsValid_thenSendCommandTrue() {
//        // given
//        val email = true
//        val password = "12345678"
//
//        // when
//        verify { viewModel.verifyInputValues(email, password) }
//
//        // then
//        assertEquals(viewModel.commandLiveData.ChangeButtonState(), true)

//    }

//    @Test
//    fun givenInputValues_whenEmailIsInvalid_thenSendCommandFalse() {
//        // given
//        val email = false
//        val password = "12345678"
//
//        // when
//        verify { viewModel.verifyInputValues(email, password) }
//
//        // then
//        assertEquals(viewModel.commandLiveData.ChangeButtonState(), false)
//
//    }

}