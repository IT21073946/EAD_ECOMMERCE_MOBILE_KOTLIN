package com.example.ecommerceapp.models

data class ProductInOrder(
    val productId: String, // Backend expects productId, not id
    val productName: String,
    val price: Double,
    val quantity: Int, // Backend requires quantity, add this field
    val vendorId: String,
    val isReady: Boolean = false
)
