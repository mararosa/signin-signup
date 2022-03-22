package com.estudos.signupsignin.signin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.estudos.signupsignin.R
import com.estudos.signupsignin.databinding.ActivitySignInBinding
import com.estudos.signupsignin.signin.viewmodel.SignInViewModel

class SignInActivity : AppCompatActivity() {

    private var _binding: ActivitySignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel = SignInViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = _binding!!.root
        setContentView(view)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}