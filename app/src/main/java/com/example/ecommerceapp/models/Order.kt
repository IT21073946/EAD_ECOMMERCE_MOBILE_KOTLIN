package com.example.ecommerceapp.models

data class Order(
    val customerId: String,
    val products: List<Product>,
    val totalAmount: Double,
    val status: String,
    val isCancelled: Boolean,
    val shippingAddress: String,
    val vendorId: String
)
