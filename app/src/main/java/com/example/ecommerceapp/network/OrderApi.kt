package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderApi {

    @POST("api/Order")
    fun createOrder(@Body order: Order): Call<Void>
}
