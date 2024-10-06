package com.example.ecommerceapp.models

data class Review(
    val userId: String,
    val vendorId: String,
    val rating: Int,
    val comment: String,
    val createdDate: String? = null,
    val updatedDate: String? = null
)
