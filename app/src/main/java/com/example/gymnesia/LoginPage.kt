package com.example.gymnesia

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val loginButton = findViewById<Button>(R.id.button2)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val passwordToggle = findViewById<ImageView>(R.id.passwordToggle) //
        val loginTextView = findViewById<TextView>(R.id.textView11)

        // disable the login button at first position
        loginButton.isEnabled = false

        // toggle password
        var isPasswordVisible = false

        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.hide) // closed eye
            } else {
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.show) // open eye
            }
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        fun isLoginValid(): Boolean {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            return email.isNotEmpty() && password.isNotEmpty() && isEmailValid
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginButton.isEnabled = isLoginValid()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        emailEditText.addTextChangedListener(watcher)
        passwordEditText.addTextChangedListener(watcher)

        loginButton.setOnClickListener {
            val intent = Intent(this@LoginPage, WelcomPage::class.java)
            startActivity(intent)
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this@LoginPage, RegisterPage::class.java)
            startActivity(intent)
        }
    }

}