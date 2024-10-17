package com.example.ecommerceapp.models

data class OrderProduct(
    val productId: String,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val vendorId: String,
    val isReady: Boolean = false // This represents if the product is ready for shipment
)
