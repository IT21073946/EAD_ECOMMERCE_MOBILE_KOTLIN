package com.example.ecommerceapp.network

import com.example.ecommerceapp.models.Vendor
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface VendorApi {

    @GET("api/Vendor/{id}")
    fun getVendorById(@Path("id") vendorId: String): Call<Vendor>
}
