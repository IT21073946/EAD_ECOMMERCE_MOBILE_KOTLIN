package com.example.ecommerceapp.models

import java.io.Serializable

data class Product(
    val id: String,
    val productName: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val vendorId: String,
): Serializable
