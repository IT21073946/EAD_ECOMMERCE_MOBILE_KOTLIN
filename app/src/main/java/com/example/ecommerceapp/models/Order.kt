package com.example.ecommerceapp.models

data class Order(
    val id: String? = null,
    val orderDate: String,
    val customerId: String,
    val products: List<ProductInOrder>, // Use the modified product class
    val totalAmount: Double,
    val status: Int, // Status should be an integer, not string
    val isCancelled: Boolean,
    val shippingAddress: String,
    val cancellationNote: String?
)

// Modified Product class for orders
data class ProductInOrder(
    val productId: String, // Backend expects productId, not id
    val productName: String,
    val price: Double,
    val quantity: Int, // Backend requires quantity, add this field
    val vendorId: String,
    val isReady: Boolean = false
)
