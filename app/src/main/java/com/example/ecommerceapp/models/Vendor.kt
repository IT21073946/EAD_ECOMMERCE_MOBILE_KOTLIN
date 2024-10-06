package com.example.ecommerceapp.models

data class Vendor(
    val id: String? = null,
    val vendorName: String,
    val email: String? = null,
    val phone: String? = null,
    val rating: Double = 0.0,
    val address: String? = null,
    val products: List<String>? = null,
    val isActive: Boolean = true
)
