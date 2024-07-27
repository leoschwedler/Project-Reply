package com.example.currencyconverter.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.currencyconverter.R
import com.example.currencyconverter.data.db.UserDAO
import com.example.currencyconverter.data.model.User
import com.example.currencyconverter.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private lateinit var userDAO: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        userDAO = UserDAO(this)
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
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            showToast("Signup failed")
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}