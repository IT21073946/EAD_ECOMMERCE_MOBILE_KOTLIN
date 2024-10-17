package com.example.ecommerceapp.models

data class Order(
    val id: String? = null,
    val orderDate: String,
    val customerId: String,
    val products: List<ProductInOrder>, // Use the modified product class
    val totalAmount: Double,
    val status: Int,  // This will store the numeric value of the status
    val isCancelled: Boolean,
    val shippingAddress: String,
    val cancellationNote: String?,
)