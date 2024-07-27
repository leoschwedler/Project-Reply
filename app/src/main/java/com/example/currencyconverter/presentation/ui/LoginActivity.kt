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
import com.example.currencyconverter.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private lateinit var userDAO: UserDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        userDAO = UserDAO(this)
        with(binding){
            buttonLogin.setOnClickListener {
                val loginEmail = binding.editTextLoginEmail.text.toString()
                val loginPassword = binding.editTextLoginPassword.text.toString()
                if (validateInputs(loginEmail, loginPassword)) {
                    val login = User(-1,"", loginEmail, loginPassword)
                    loginDataBase(login)}

                /*val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)*/
                }


            textViewRegisterNow.setOnClickListener {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun loginDataBase(user: User) {
        val existingUser = userDAO.readUser(user)
        if (existingUser) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                showToast("Please enter a email")
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
