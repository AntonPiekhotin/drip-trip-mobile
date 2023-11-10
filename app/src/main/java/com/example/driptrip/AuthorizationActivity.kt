package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // TODO: Авторизувати користувача
        }
    }

    fun onLoginClick(view: View) {
        startActivity(Intent(this, RegistrationActivity::class.java))
        finish()
    }
}