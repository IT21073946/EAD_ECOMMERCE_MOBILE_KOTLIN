package com.example.ecommerceapp.controllers

import android.content.Context
import com.example.ecommerceapp.models.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderController(private val context: Context) {

    // Fetch all orders
    fun getOrders(callback: (List<Order>?, String?) -> Unit) {
        val call = ApiClient.orderApi.getAllOrders()

        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null) // Success, pass the order list
                } else {
                    callback(null, "Failed to fetch orders: ${response.message()}") // Error message
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                callback(null, "Error: ${t.message}") // Network error or failure
            }
        })
    }

    // Create a new order (convert status string to integer when sending to the API)
    fun createOrder(order: Order, callback: (Boolean, String?) -> Unit) {
        val orderWithIntStatus = order.copy(status = order.getStatusName().toString())  // Convert status to int string

        val call = ApiClient.orderApi.createOrder(orderWithIntStatus)

        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    callback(true, "Order created successfully!") // Success, notify the caller
                } else {
                    callback(false, "Failed to create order: ${response.message()}") // Error message
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                callback(false, "Error: ${t.message}") // Network error or failure
            }
        })
    }

    fun getOrdersByCustomerId(customerId: String, callback: (List<Order>?, String?) -> Unit) {
        val call = ApiClient.orderApi.getOrdersByCustomerId(customerId)

        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null) // Success, pass the order list
                } else {
                    callback(null, "Failed to fetch orders: ${response.message()}") // Error message
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                callback(null, "Error: ${t.message}") // Network error or failure
            }
        })
    }

    fun getOrderById(orderId: String, callback: (Order?, String?) -> Unit) {
        val call = ApiClient.orderApi.getOrderById(orderId)

        call.enqueue(object : Callback<Order> {
            override fun onResponse(call: Call<Order>, response: Response<Order>) {
                if (response.isSuccessful) {
                    callback(response.body(), null) // Success, pass the order object
                } else {
                    callback(null, "Failed to fetch order: ${response.message()}") // Error message
                }
            }

            override fun onFailure(call: Call<Order>, t: Throwable) {
                callback(null, "Error: ${t.message}") // Network error or failure
            }
        })
    }
}
