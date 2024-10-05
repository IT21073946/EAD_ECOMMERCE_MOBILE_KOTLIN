package com.example.ecommerceapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://11fc-2402-4000-21c3-fa22-1a9-ca12-184-52e2.ngrok-free.app"  // Add https://

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
