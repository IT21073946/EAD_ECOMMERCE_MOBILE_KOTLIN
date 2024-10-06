package com.example.ecommerceapp.controllers

import android.content.Context
import android.util.Log
import com.example.ecommerceapp.models.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderController(private val context: Context) {

    fun createOrder(order: Order, callback: (Boolean, String?) -> Unit) {
        val call = ApiClient.orderApi.createOrder(order)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    callback(true, "Order created successfully!")
                } else {
                    callback(false, "Failed to create order: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                callback(false, "Error: ${t.message}")
            }
        })
    }
}
