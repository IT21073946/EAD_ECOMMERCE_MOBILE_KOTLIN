package com.example.ecommerceapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://67b3-2402-4000-20c3-82f1-d898-53ff-feb8-ebf2.ngrok-free.app/"  // Add https://

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    // Initialize the Product API
    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

}
