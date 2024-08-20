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
import com.example.currencyconverter.databinding.FragmentSignupBinding
import com.example.currencyconverter.presentation.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private val signupViewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        observeViewModel()
    }

    private fun initListeners() {
        with(binding) {
            buttonCreateAccount.setOnClickListener {
                val username = editTextRegisterUsername.text.toString()
                val email = editTextRegisterEmail.text.toString()
                val password = editTextRegisterPassword.text.toString()

                if (signupViewModel.validateInputs(username, email, password)) {
                    val user = User(-1, username, email, password)
                    signupViewModel.signUp(user)
                }
            }

            textViewLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        }
    }

    private fun observeViewModel() {
        signupViewModel.signUpResult.observe(viewLifecycleOwner, Observer { result ->
            if (result != -1L) {
                Toast.makeText(context, "Signup Successful", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            } else {
                Toast.makeText(context, "Signup Failed", Toast.LENGTH_SHORT).show()
            }
        })

        signupViewModel.inputValidationError.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
