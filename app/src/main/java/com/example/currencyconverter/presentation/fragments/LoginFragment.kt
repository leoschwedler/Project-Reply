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
import com.example.currencyconverter.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userDAO: UserDAO

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar o UserDAO com o contexto do fragmento
        userDAO = UserDAO(requireContext())

        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            buttonLogin.setOnClickListener {
                val loginEmail = binding.editTextLoginEmail.text.toString()
                val loginPassword = binding.editTextLoginPassword.text.toString()

                if (validateInputs(loginEmail, loginPassword)) {
                    val loginUser = User(-1, "", loginEmail, loginPassword)
                    loginDataBase(loginUser)
                }
            }

            textViewRegisterNow.setOnClickListener {
                // Navegar para o fragment de Signup
                findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
            }
        }
    }

    private fun loginDataBase(user: User) {
        val existingUser = userDAO.readUser(user)
        if (existingUser) {
            showToast("Login Successful")
            // Navegar para o MainActivity ou outra tela de sucesso
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        } else {
            showToast("Login Failed")
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Please enter an email")
                false
            }
            password.isEmpty() -> {
                showToast("Please enter a password")
                false
            }
            else -> true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
