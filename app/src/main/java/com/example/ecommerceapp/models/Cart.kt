package com.example.ecommerceapp.models

class Cart private constructor() {

    private val products = mutableListOf<Product>()

    // Singleton instance
    companion object {
        private var instance: Cart? = null
        fun getInstance(): Cart {
            if (instance == null) {
                instance = Cart()
            }
            return instance!!
        }
    }

    // Add product to cart
    fun addProduct(product: Product) {
        products.add(product)
    }

    // Remove product from cart
    fun removeProduct(product: Product) {
        products.remove(product)
    }

    // Get all products in cart
    fun getProducts(): List<Product> {
        return products
    }

    // Clear cart
    fun clearCart() {
        products.clear()
    }
}
