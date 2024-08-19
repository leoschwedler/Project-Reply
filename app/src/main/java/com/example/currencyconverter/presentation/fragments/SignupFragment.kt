package com.example.currencyconverter.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.currencyconverter.R
import com.example.currencyconverter.data.db.UserDAO
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicialize o UserDAO com o contexto do fragmento
        userDAO = UserDAO(requireContext())

        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            buttonCreateAccount.setOnClickListener {
                val signupEmail = binding.editTextRegisterEmail.text.toString()
                val signUpPassword = binding.editTextRegisterPassword.text.toString()
                val signUpUser = binding.editTextRegisterUsername.text.toString()

                if (validateInputs(signupEmail, signUpUser, signUpPassword)) {
                    val newUser = User(-1, signUpUser, signupEmail, signUpPassword)
                    signUpDataBase(newUser)
                }
            }
            textViewLogin.setOnClickListener {
                findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
            }
        }
    }

    private fun validateInputs(email: String, username: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                showToast("Please enter a username")
                false
            }

            email.isEmpty() -> {
                showToast("Please enter an email")
                false
            }

            password.isEmpty() -> {
                showToast("Please enter a password")
                false
            }

            password.length < 6 -> {
                showToast("Password must be at least 6 characters long")
                false
            }

            else -> true
        }
    }

    private fun signUpDataBase(user: User) {
        binding.buttonCreateAccount.isEnabled = false
        val insertRowId = userDAO.insertUser(user)
        binding.buttonCreateAccount.isEnabled = true
        if (insertRowId != -1L) {
            showToast("Signup successful")
            // Navegar para a LoginFragment ou MainActivity, dependendo do seu fluxo
            findNavController().navigate(R.id.action_signupFragment_to_mainActivity)
        } else {
            showToast("Signup failed")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
