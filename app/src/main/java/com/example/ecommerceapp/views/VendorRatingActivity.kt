package com.example.ecommerceapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.databinding.ActivityVendorRatingBinding

class VendorRatingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityVendorRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the ActionBar with back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Retrieve vendor ID passed from the previous activity
        val vendorId = intent.getStringExtra("vendorId")
        binding.vendorIdText.text = "Vendor ID: $vendorId"

        // Handle rating submission
        binding.submitButton.setOnClickListener {
            // Add code to submit the rating and comment
        }

    }

    // Handle back button click
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Go back to the previous activity
        return true
    }
}
