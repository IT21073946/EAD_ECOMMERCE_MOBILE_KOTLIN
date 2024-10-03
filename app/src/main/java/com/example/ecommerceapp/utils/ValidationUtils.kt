package com.example.ecommerceapp.utils

import android.util.Patterns

object ValidationUtils {

    // Validates if the email follows a valid format
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Validates if the password is at least 6 characters long
    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    // Ensures input is not empty
    fun isNotEmpty(input: String): Boolean {
        return input.isNotEmpty()
    }
}
