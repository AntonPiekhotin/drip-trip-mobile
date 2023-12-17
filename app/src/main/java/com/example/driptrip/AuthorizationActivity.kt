package com.example.driptrip

import android.content.Intent
import android.database.Cursor
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
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnToRegistration = findViewById(R.id.to_registration)
        btnLogin = findViewById(R.id.btn_login)
        dbHelper = DBHelper(this)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (isUserExists(email, password)) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                //store id globally
                DripTripApp.userId = dbHelper.getUserId(email);
                startActivity(Intent(this, OrdersAvailableActivity::class.java))
            } else {
                Toast.makeText(this, "Невірний логін або пароль", Toast.LENGTH_SHORT).show()
            }
        }
        btnToRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun isUserExists(email: String, password: String): Boolean {
        val columns = arrayOf(DBHelper.COLUMN_ID)
        val selection = "${DBHelper.COLUMN_EMAIL} = ? AND ${DBHelper.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(email, password)
        val database = dbHelper.writableDatabase

        val cursor: Cursor = database!!.query(
            DBHelper.TABLE_USERS,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val count = cursor.count
        cursor.close()
        database.close()
        return count > 0
    }
}