package com.example.driptrip

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class RegistrationActivity : AppCompatActivity() {

    private lateinit var etUsername: AppCompatEditText
    private lateinit var etEmail: AppCompatEditText
    private lateinit var etPassword: AppCompatEditText
    private lateinit var etPassword2: AppCompatEditText
    private lateinit var etFirstName: AppCompatEditText
    private lateinit var etLastName: AppCompatEditText
    private lateinit var btnToAuthorization: AppCompatTextView
    private lateinit var btnRegister: AppCompatButton
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etUsername = findViewById(R.id.et_username)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etPassword2 = findViewById(R.id.et_password2)
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        btnRegister = findViewById(R.id.btn_register)
        btnToAuthorization = findViewById(R.id.to_authorization)
        dbHelper = DBHelper(this)

        val string = "Sign in"
        val spannableString = SpannableString(string)
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
        btnToAuthorization.text = spannableString.toString()

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val password2 = etPassword2.text.toString()
            val lastName = etLastName.text.toString()
            val firstName = etFirstName.text.toString()

            if (validateRegistration(username, email, password, password2)) {
                val database = dbHelper.writableDatabase
                val values = ContentValues()
                val dateTime = LocalDateTime.now().toString();

                values.put(DBHelper.COLUMN_USERNAME, username)
                values.put(DBHelper.COLUMN_PASSWORD, password)
                values.put(DBHelper.COLUMN_EMAIL, email)
                values.put(DBHelper.COLUMN_FIRST_NAME, firstName)
                values.put(DBHelper.COLUMN_LAST_NAME, lastName)
                values.put(DBHelper.COLUMN_DATE_JOINED, dateTime)
                values.put(DBHelper.COLUMN_IS_STAFF, false)
                values.put(DBHelper.COLUMN_IS_SUPERUSER, false)
                values.put(DBHelper.COLUMN_IS_ACTIVE, true)

                val result = database!!.insert(DBHelper.TABLE_USERS, null, values)
                database.close()

                if (result != -1L) {
//                    etUsername.setText("")
//                    etEmail.setText("")
//                    etPassword.setText("")
//                    etLastName.setText("")
//                    etFirstName.setText("")
//                    etPassword2.setText("")
                    Toast.makeText(this, "User registered!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, OrdersAvailableActivity::class.java))
                } else {
                    Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnToAuthorization.setOnClickListener {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }

    }

    private fun validateRegistration(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (email == "") {
            Toast.makeText(this, "Введіть Email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Невірний формат Email", Toast.LENGTH_SHORT).show()
            return false
        }
        if (checkUserExists(username, email)) {
            Toast.makeText(
                this,
                "Користувач з таким логіном або Email вже існує",
                Toast.LENGTH_SHORT
            )
                .show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(this, "Пароль повинен бути не менше 6 символів", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Паролі не співпадають", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun checkUserExists(username: String, email: String): Boolean {
        val columns = arrayOf(DBHelper.COLUMN_ID)
        val selection = "${DBHelper.COLUMN_USERNAME} = ? AND ${DBHelper.COLUMN_EMAIL} = ?"
        val selectionArgs = arrayOf(username, email)
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
