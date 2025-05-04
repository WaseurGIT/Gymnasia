package com.example.gymnesia

//import com.example.gymnesia.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView


class RegisterPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameEditText = findViewById<EditText>(R.id.editTextText)
        val emailEditText = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordEditText = findViewById<EditText>(R.id.editTextTextPassword)
        val passwordToggle = findViewById<ImageView>(R.id.passwordToggle)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val registerButton = findViewById<Button>(R.id.button)
        val loginTextView = findViewById<TextView>(R.id.textView10)

        // Disable register button initially
        registerButton.isEnabled = false

        var isPasswordVisible = false

        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Show password
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordToggle.setImageResource(R.drawable.hide) // Use closed-eye icon
            } else {
                // Hide password
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordToggle.setImageResource(R.drawable.show) // Use open-eye icon
            }
            // Move cursor to end after toggling
            passwordEditText.setSelection(passwordEditText.text.length)
        }

        fun isFormValid(): Boolean {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            return name.isNotEmpty() && isEmailValid && password.isNotEmpty() && checkBox.isChecked
        }

        // Check all fields when anything changes
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                registerButton.isEnabled = isFormValid()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        nameEditText.addTextChangedListener(watcher)
        emailEditText.addTextChangedListener(watcher)
        passwordEditText.addTextChangedListener(watcher)

        checkBox.setOnCheckedChangeListener { _, _ ->
            registerButton.isEnabled = isFormValid()
        }

        registerButton.setOnClickListener {
            // Proceed to next page
            val intent = Intent(this@RegisterPage, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        loginTextView.setOnClickListener {
            val intent = Intent(this@RegisterPage, LoginPage::class.java)
            startActivity(intent)
        }
    }

}