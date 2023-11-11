package com.example.driptrip

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.*
import androidx.appcompat.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etUsername: AppCompatEditText
    private lateinit var etEmail: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var btnToAuthorization: AppCompatTextView
    private lateinit var btnRegister: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnRegister = findViewById(R.id.btn_register)
        btnToAuthorization = findViewById(R.id.to_authorization)

//        val signIn = findViewById<TextView>(R.id.to_authorization)

        val string = "Sign in"
        val spannableString = SpannableString(string)
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
        btnToAuthorization.text = spannableString.toString()

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
        }

        btnToAuthorization.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }

        //TODO: connect to django server (?)

    }
}
