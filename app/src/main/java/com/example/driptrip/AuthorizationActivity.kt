package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.*
import androidx.appcompat.app.AppCompatActivity

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var etEmail: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var btnToRegistration: AppCompatTextView
    private lateinit var btnLogin: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnToRegistration = findViewById(R.id.to_registration)
        btnLogin = findViewById(R.id.btn_login)


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

        }
        btnToRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}