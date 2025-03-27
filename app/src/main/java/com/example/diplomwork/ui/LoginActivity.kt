package com.example.diplomwork.ui

import User
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomwork.R
import com.example.diplomwork.ui.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener { loginUser() }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.login(User(email, password))
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT)
                        .show()
                    // Перейти в основное активити
                } else {
                    Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}