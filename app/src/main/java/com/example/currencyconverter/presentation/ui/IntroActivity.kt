package com.example.currencyconverter.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        with(binding){
            buttonLogin.setOnClickListener {
                val intent = Intent(this@IntroActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            textCreateAccount.setOnClickListener {
                val intent = Intent(this@IntroActivity, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}