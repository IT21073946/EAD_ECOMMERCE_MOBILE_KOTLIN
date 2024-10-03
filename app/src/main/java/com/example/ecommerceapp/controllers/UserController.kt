package com.example.ecommerceapp.controllers

import com.example.ecommerceapp.models.User
import com.example.ecommerceapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserController {

    // Function to create a user
    fun registerUser(user: User, callback: (Boolean, String?) -> Unit) {
        val call = ApiClient.userApi.createUser(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    callback(true, "User registered successfully")
                } else {
                    callback(false, "Registration failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback(false, "Error: ${t.message}")
            }
        })
    }
}
