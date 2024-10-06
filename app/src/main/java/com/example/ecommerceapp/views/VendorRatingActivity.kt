package com.example.ecommerceapp.views

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerceapp.R
import com.example.ecommerceapp.models.Review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VendorRatingActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentInput: EditText
    private lateinit var submitButton: Button
    private lateinit var vendorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_rating)

        // Bind UI elements to the variables
        ratingBar = findViewById(R.id.ratingBar)
        commentInput = findViewById(R.id.commentInput)
        submitButton = findViewById(R.id.submitButton)

        // Retrieve vendorId from the intent
        vendorId = intent.getStringExtra("vendorId") ?: ""

        // Set up submit button listener
        submitButton.setOnClickListener {
            submitRating()
        }
    }

    private fun submitRating() {
        // Get the rating and comment entered by the user
        val rating = ratingBar.rating.toInt()
        val comment = commentInput.text.toString()

        // Dummy userId (replace with actual userId)
        val userId = "66fdf2312956d4cb29ac6fb7" // Replace with actual user ID from your app logic

        // Create Review object
        val review = Review(userId, vendorId, rating, comment)

        // Post the review using the API
        ApiClient.reviewApi.postReview(review).enqueue(object : Callback<Review> {
            override fun onResponse(call: Call<Review>, response: Response<Review>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@VendorRatingActivity, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity and return to previous screen
                } else {
                    Toast.makeText(this@VendorRatingActivity, "Failed to submit review", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Review>, t: Throwable) {
                Toast.makeText(this@VendorRatingActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
