package com.example.diplomwork.ui

import User
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomwork.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.core.content.edit

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        // Логирование для отладки
        Log.d("LoginActivity", "Attempting login with email: $email")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiService.login(User(email, password))
                withContext(Dispatchers.Main) {
                    if (!response?.accessToken?.isEmpty()!!) {
                        // Сохранение токена в SharedPreferences
                        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
                        sharedPreferences.edit {
                            putString("access_token", response.accessToken)
                        }

                        // Логирование успешного логина
                        Log.d("LoginActivity", "Login successful. Access token: ${response.accessToken}")

                        // Переход в MainActivity
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()  // Закрытие текущей активности
                    } else {
                        Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Логирование ошибки
                withContext(Dispatchers.Main) {
                    Log.e("LoginActivity", "Error during login: ${e.message}")
                    Toast.makeText(this@LoginActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
