package com.example.currencyconverter.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.currencyconverter.R
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.databinding.FragmentLoginBinding
import com.example.currencyconverter.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()
    }

    private fun initListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val email = editTextLoginEmail.text.toString()
                val password = editTextLoginPassword.text.toString()

                if (loginViewModel.validateInputs(email, password)) {
                    val user = User(-1, "", email, password)
                    loginViewModel.login(user)
                }
            }

            textViewRegisterNow.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }
    }

    private fun observeViewModel() {
        loginViewModel.loginSuccess.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            } else {
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        })

        loginViewModel.inputValidationError.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
