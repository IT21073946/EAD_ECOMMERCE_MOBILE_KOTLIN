package com.example.ecommerceapp.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var userController: UserController
    private lateinit var signupRedirectText: TextView  // Add this for the signup text

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userController = UserController(this)

        emailInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        signupRedirectText = findViewById(R.id.signupRedirectText)  // Initialize the signup redirect TextView
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            loginUser()
        }

        // Add this block to handle "Sign Up Now" text click and navigate to RegisterActivity
        signupRedirectText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            return
        }

        userController.loginUser(email, password) { success, message ->
            if (success) {
                // Extract userId from the response (assuming it's included in the LoginResponse)
                val userId = message // Assuming message is where the userId is passed, adjust as needed
                saveLoggedInUserEmail(email)

                Toast.makeText(this, "Login successful: $userId", Toast.LENGTH_SHORT).show()

                // Pass the userId to the HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("USER_ID", userId)  // Add userId as extra to the intent
                startActivity(intent)
                finish()  // Close LoginActivity after navigating to home
            } else {
                Toast.makeText(this, message ?: "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

    }

    // Save email to SharedPreferences after login
    private fun saveLoggedInUserEmail(email: String) {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("logged_in_email", email)
        editor.apply()
    }

}
