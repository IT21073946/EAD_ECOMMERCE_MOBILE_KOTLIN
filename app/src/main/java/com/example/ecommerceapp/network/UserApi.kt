package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.User  // Import only your custom User model
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    // POST request for creating a new user
    @POST("api/User")
    fun createUser(@Body user: User): Call<User>

}
