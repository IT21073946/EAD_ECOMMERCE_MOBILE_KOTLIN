package com.example.ecommerceapp.models
import java.io.Serializable

data class Product(
    val id: String? = null,
    val productName: String,
    val description: String? = null,
    val price: Double,
    val category: String? = null,
    val vendorId: String,
    val stock: Int,
    val isActive: Boolean,
    val rating: Double,
    val base64Image: String? = null
): Serializable


