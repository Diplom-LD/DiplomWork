package com.example.diplomwork.ui

import User
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomwork.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.apiService.register(User(email, password))
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}