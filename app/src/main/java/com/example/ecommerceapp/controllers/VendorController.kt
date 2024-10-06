package com.example.ecommerceapp.controllers

import com.example.ecommerceapp.models.Vendor
//import com.example.ecommerceapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorController {

    fun getVendorById(vendorId: String, callback: (Vendor?) -> Unit) {
        val call = ApiClient.vendorApi.getVendorById(vendorId)

        call.enqueue(object : Callback<Vendor> {
            override fun onResponse(call: Call<Vendor>, response: Response<Vendor>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Vendor>, t: Throwable) {
                callback(null)
            }
        })
    }
}
