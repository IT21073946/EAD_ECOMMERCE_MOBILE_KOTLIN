package com.example.ecommerceapp.models

data class User(
    val id: String? = null,
    val email: String,
    val username: String,
    val password: String,
    val role: String = "",
    val isActive: Boolean = false,
    val profilePictureBase64: String? = null,
    val contactNumber: String? = null
)




