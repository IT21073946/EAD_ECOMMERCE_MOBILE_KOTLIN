package com.example.ecommerceapp.controllers

import android.content.Context
import com.example.ecommerceapp.models.Order
import android.util.Log
import com.example.ecommerceapp.models.Order
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderController (private val context: Context) {

    fun getOrders(callback: (List<Order>?, String?) -> Unit) {
        val call = ApiClient.orderApi.getAllOrders()

        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, "Failed to fetch orders")
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                callback(null, "Error: ${t.message}")
            }
        })
    }
}
