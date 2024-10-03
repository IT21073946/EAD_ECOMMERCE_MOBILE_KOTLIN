package com.example.ecommerceapp.views

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.controllers.UserController
import com.example.ecommerceapp.models.User
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var emailInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var confirmPasswordInput: TextInputEditText
    private lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userController = UserController()

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        val registerButton = findViewById<Button>(R.id.registerButton)

        // Handle register button click
        registerButton.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = usernameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val confirmPassword = confirmPasswordInput.text.toString().trim()

        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Create the user object
        val user = User(
            email = email,
            username = username,
            password = password,
            role = "Customer"  // Adjust the role as needed
        )

        // Call the controller to register the user
        userController.registerUser(user) { success, message ->
            if (success) {
                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, message ?: "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
